package pers.ken.rt.gw.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.gw.utils.RequestLog;
import pers.ken.rt.gw.utils.RequestLogHelper;
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
        String requestId = RequestLogHelper.genRequestId();
        String startTimeMillions = String.valueOf(System.currentTimeMillis());
        // 日志记录
        // 上传文件简单记录
        RequestLog requestLog = new RequestLog();
        //此处创建ServerRequest一定要传入messageReaders，不能使用ServerRequest serverRequest = new DefaultServerRequest(exchange);创建
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        URI requestUri = serverHttpRequest.getURI();
        String method = serverHttpRequest.getMethod().name().toUpperCase();
        MediaType mediaType = serverHttpRequest.getHeaders().getContentType();
        Mono<byte[]> modifyBody = serverRequest.bodyToMono(byte[].class).flatMap(o -> {
            if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                return Mono.justOrEmpty(o);
            }
            return Mono.empty();
        });
        BodyInserter<Mono<byte[]>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifyBody, byte[].class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        headers.set(HttpHeaders.CONTENT_TYPE, serverHttpRequest.getHeaders().getFirst("Content-Type"));
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> chain.filter(exchange.mutate()
                                .request(new ServerHttpRequestDecorator(exchange.getRequest()) {
                                    @Override
                                    public HttpHeaders getHeaders() {
                                        long contentLength = headers.getContentLength();
                                        HttpHeaders httpHeaders = new HttpHeaders();
                                        httpHeaders.putAll(super.getHeaders());
                                        httpHeaders.set(HttpHeaderCons.REQUEST_ID, requestId);
                                        httpHeaders.set(HttpHeaderCons.REQUEST_START_TIME_HEADER, startTimeMillions);
                                        if (contentLength > 0) {
                                            httpHeaders.setContentLength(contentLength);
                                        } else {
                                            httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                        }
                                        requestLog.setRequestHeaders(httpHeaders.toString());
                                        return httpHeaders;
                                    }

                                    @Override
                                    public Flux<DataBuffer> getBody() {
                                        return outputMessage.getBody();
                                    }
                                })
                                .response(new ServerHttpResponseDecorator(exchange.getResponse()) {
                                    @Override
                                    public HttpHeaders getHeaders() {
                                        HttpHeaders responseHeader = super.getHeaders();
                                        if (StringUtils.isNotBlank(requestId)) {
                                            responseHeader.set(HttpHeaderCons.REQUEST_ID, requestId);
                                            responseHeader.set(HttpHeaderCons.REQUEST_START_TIME_HEADER, startTimeMillions);
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
                                                DataBuffer dataBuffer = dataBufferFactory.join(dataBuffers);
                                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                                dataBuffer.read(content);
                                                // 释放掉内存
                                                DataBufferUtils.release(dataBuffer);
                                                HttpHeaders headers = super.getHeaders();
                                                MediaType mediaType = headers.getContentType();
                                                if (RequestLogHelper.isUploadFile(mediaType)) {
                                                    requestLog.setResponseBody("Ignore resp body when not json");
                                                } else {
                                                    Charset charset =
                                                            RequestLogHelper.getMediaTypeCharset(mediaType);
                                                    String responseBody = new String(content, charset);
                                                    requestLog.setResponseBody(StringUtils.substring(responseBody, 0, 2000));
                                                }
                                                long startTime = RequestLogHelper.getStartTime(headers);
                                                long handleTime = RequestLogHelper.getHandleTime(startTime);
                                                requestLog.setRequestId(requestId);
                                                requestLog.setUrl(requestUri.toString());
                                                requestLog.setQueryParams(serverHttpRequest.getURI().getQuery());
                                                if (null != mediaType) {
                                                    requestLog.setRespContentType(mediaType.toString());
                                                }
                                                requestLog.setMethod(method);
                                                if (null != exchange.getResponse().getStatusCode()) {
                                                    requestLog.setHttpStatus(exchange.getResponse().getStatusCode().value());
                                                }
                                                requestLog.setStartTimeMillis(startTime);
                                                requestLog.setExecuteTimeMillis(handleTime);

                                                return exchange.getResponse().bufferFactory().wrap(content);
                                            }));
                                        }
                                        return super.writeWith(body);
                                    }
                                })
                                .build()
                        )
                        .then(Mono.fromRunnable(() -> {
                            // 打印日志
                            requestLog.setRequestId(requestId);
                            log.info(requestLog.toString());
                        }))));
    }
}

