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
        String requestId = RequestLogHelper.genRequestId();
        String startTimeMillions = String.valueOf(System.currentTimeMillis());
        // ????????????
        // ????????????????????????
        RequestLog requestLog = new RequestLog();
        //????????????ServerRequest???????????????messageReaders???????????????ServerRequest serverRequest = new DefaultServerRequest(exchange);??????
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        URI requestUri = serverHttpRequest.getURI();
        String method = serverHttpRequest.getMethodValue().toUpperCase();
        MediaType mediaType = serverHttpRequest.getHeaders().getContentType();
        Mono<String> modifyBody = serverRequest.bodyToMono(String.class).flatMap(o -> {
            if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                return Mono.justOrEmpty(o);
            }
            return Mono.empty();
        });
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifyBody, String.class);
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
                                                // ???????????????????????????????????????????????????
                                                DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                                                DataBuffer dataBuffer = dataBufferFactory.join(dataBuffers);
                                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                                dataBuffer.read(content);
                                                // ???????????????
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
                                                requestLog.setRequestHeaders(headers.toString());
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
                            // ????????????
                            requestLog.setRequestId(requestId);
                            log.info(requestLog.toString());
                        }))));
    }
}

