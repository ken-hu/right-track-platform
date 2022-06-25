package pers.ken.rt.gw.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.exception.ServiceCode;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.utils.Jackson;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <code> GlobalExceptionHandler </code>
 * <desc> GlobalExceptionHandler </desc>
 * <b>Creation Time:</b> 2022/5/12 23:32.
 *
 * @author Ken.Hu
 */
@Slf4j
@Order(-1)
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 设置返回JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            ServerHttpRequest request = exchange.getRequest();
            //返回响应结果
            return bufferFactory.wrap(Jackson.toJsonString(
                            new PlatformError(ServiceCode.FAILED.getCode(),
                                    ServiceCode.FAILED.getMessage(), ServiceCode.FAILED.getMessage(),
                                    request.getHeaders().getFirst(HttpHeaderCons.REQUEST_ID),
                                    request.getURI().toString()
                            ))
                    .getBytes(StandardCharsets.UTF_8));

        }));
    }
}
