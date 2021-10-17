package pers.ken.rt.gw.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.*;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.gw.log.HttpHeaderCons;
import pers.ken.rt.gw.log.RequestLog;
import pers.ken.rt.gw.log.RequestLogHelper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <code>RequestLogFilter</code>
 * <desc> <desc/>
 * <b>Creation Time:</b> 2021/5/24 21:45.
 *
 * @author _Ken.Hu
 */
@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {
    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
        String requestId = RequestLogHelper.genRequestId();

        // 日志记录
        RequestLog requestLog = new RequestLog();
        // 上传文件简单记录
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers
                = request.getHeaders();
        MediaType mediaType = headers.getContentType();
        URI requestUri = request.getURI();
        String schema = requestUri.getScheme();
        String method = request.getMethodValue().toUpperCase();
        String uriQuery = requestUri.getQuery();

        if (!RequestLogHelper.isNormalRequest(schema)) {
            return chain.filter(exchange);
        }

        if (RequestLogHelper.isUploadFile(mediaType)) {
            requestLog.setRequestBody("Ignore : Upload file ");
            return chain.filter(exchange);
        }
        if (method.equals(HttpMethod.GET.name())) {
            if (!StringUtils.isEmpty(uriQuery)) {
                return getHandler(exchange, chain, requestLog, uriQuery);
            }
        }
        if (headers.getContentLength() > 0) {
            return postHandler(exchange, chain, requestLog, requestId,
                    System.currentTimeMillis());
        }
        return chain.filter(exchange);
    } catch (Exception e) {
        log.warn("Failed to remark request log", e);
        return chain.filter(exchange);
    }
}

    private Mono<Void> getHandler(ServerWebExchange exchange, GatewayFilterChain chain, RequestLog requestLog,
                                  String uriQuery) {
        requestLog.setUriQueryParams(uriQuery);
        ServerHttpResponseDecorator serverHttpResponseDecorator = getServerHttpResponseDecorator(exchange, requestLog);
        return chain
                .filter(exchange.mutate().response(serverHttpResponseDecorator).build())
                .then(Mono.fromRunnable(() -> {
                    // 打印日志
                    requestLog.setUriQueryParams(uriQuery);
                    RequestLogHelper.doRecord(requestLog);
                }));
    }

    private Mono<Void> postHandler(ServerWebExchange exchange, GatewayFilterChain chain, RequestLog requestLog,
                                   String requestId, long startTime) {
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                .flatMap(body -> {
                    // 记录body内容
                    requestLog.setRequestBody(body);
                    return Mono.just(body);
                });

        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody
                , String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    // 重新封装请求
                    ServerHttpRequestDecorator requestDecorator =
                            getServerHttpRequestDecorator(exchange, headers, outputMessage, requestId, startTime);
                    ServerWebExchange serverWebExchange = exchange.mutate().request(requestDecorator).build();

                    // 记录响应日志
                    ServerHttpResponseDecorator decoratedResponse =
                            getServerHttpResponseDecorator(serverWebExchange, requestLog);

                    return chain.filter(exchange.mutate().request(requestDecorator).response(decoratedResponse).build())
                            .then(Mono.fromRunnable(() -> {
                                // 打印日志
                                RequestLogHelper.doRecord(requestLog);
                            }));
                }));
    }

    private ServerHttpResponseDecorator getServerHttpResponseDecorator(ServerWebExchange exchange,
                                                                       RequestLog requestLog) {

        // 获取response的返回数据
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        HttpStatus httpStatus = originalResponse.getStatusCode();
        ServerHttpRequest request = exchange.getRequest();
        URI requestUri = request.getURI();
        String uriQuery = requestUri.getQuery();
        HttpHeaders headers = request.getHeaders();
        MediaType mediaType = headers.getContentType();
        String method = request.getMethodValue().toUpperCase();
        String requestId = headers.getFirst(HttpHeaderCons.REQUEST_ID_HEADER);

        // 封装返回体
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public HttpHeaders getHeaders() {
                String traceId = MDC.get(HttpHeaderCons.TRACE_ID_HEADER);

                HttpHeaders responseHeader = super.getHeaders();
                if (!StringUtils.isEmpty(traceId)){
                    responseHeader.set(HttpHeaderCons.TRACE_ID_HEADER, traceId);
                }
                return responseHeader;
            }

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        // 合并多个流集合，解决返回体分段传输
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        // 释放掉内存
                        DataBufferUtils.release(join);
                        Charset charset =
                                RequestLogHelper.getMediaTypeCharset(originalResponse.getHeaders().getContentType());

                        String responseBody = new String(content, charset);
                        long startTime = RequestLogHelper.getStartTime(headers);
                        long handleTime = RequestLogHelper.getHandleTime(startTime);
                        requestLog.setRequestId(requestId);
                        requestLog.setRequestUrl(requestUri.toString());
                        requestLog.setUriQueryParams(uriQuery);
                        requestLog.setHeaders(headers.toString());
                        if (null != mediaType) {
                            requestLog.setMediaType(mediaType.toString());
                        }
                        requestLog.setResponseBody(responseBody);
                        requestLog.setHttpMethod(method);
                        if (null != httpStatus) {
                            requestLog.setHttpStatus(httpStatus.value());
                        }
                        requestLog.setStartTimeMillis(startTime);
                        requestLog.setHandleTimeMillis(handleTime);

                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body);
            }
        };
    }


    private ServerHttpRequestDecorator getServerHttpRequestDecorator(ServerWebExchange exchange, HttpHeaders headers,
                                                                     CachedBodyOutputMessage outputMessage,
                                                                     String requestId, long startTime) {

        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
            /*    String headerRequestId = headers.getFirst(HttpHeaderCons.REQUEST_ID_HEADER);
                if (StringUtils.isEmpty(headerRequestId)) {
                    httpHeaders.set(HttpHeaderCons.REQUEST_ID_HEADER, requestId);
                    MDC.put(HttpHeaderCons.REQUEST_ID_HEADER, requestId);
                }*/
                httpHeaders.set(HttpHeaderCons.REQUEST_START_TIME_HEADER, String.valueOf(startTime));

                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

}

