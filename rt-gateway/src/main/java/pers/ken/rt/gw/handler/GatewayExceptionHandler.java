package pers.ken.rt.gw.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.common.exception.ErrorCode;
import pers.ken.rt.common.model.ErrorResponse;
import pers.ken.rt.common.utils.Jackson;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <code> GlobalExceptionHandler </code>
 * <desc>
 * rt-common里面的全局异常处理无法处理request未打到对应的micro_service的情况，
 * 实际上gateway未出现异常，未经过任何格式化的处理的Spring默认处理的响应格式会透给Client端
 * 因此必须在网关中也要定制一层全局异常处理 统一对Client端的响应信息
 * </desc>
 * <b>Creation Time: 2022/5/12 23:32.
 *
 * @author Ken.Hu
 */
@Slf4j
@Order(-1)
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 设置返回JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatusCode());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            //返回响应结果
            return bufferFactory.wrap(Jackson.toJsonString(
                            ErrorResponse.of(ErrorCode.FAILED, ex.getMessage())
                    )
                    .getBytes(StandardCharsets.UTF_8));
        }));

    }
}
