package pers.ken.rt.gw.oauth.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.utils.Jackson;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static pers.ken.rt.common.exception.ServiceCode.PERMISSION_NOT_ENOUGH;

/**
 * <code>ServiceAccessDenieHandler</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/6/10 22:13.
 *
 * @author _Ken.Hu
 */
@Slf4j
@Component
public class Oauth2AccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException accessDeniedException) {
        log.error("Occur AccessDeniedException", accessDeniedException);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String responseStr = Jackson.toJsonString(
                PlatformError.builder()
                        .code(PERMISSION_NOT_ENOUGH.getCode())
                        .message(PERMISSION_NOT_ENOUGH.getMessage())
                        .detail(accessDeniedException.getMessage())
                        .requestId(exchange.getRequest().getHeaders().getFirst(HttpHeaderCons.REQUEST_ID))
                        .path(exchange.getRequest().getPath().value())
                        .build()
        );
        DataBuffer buffer = response.bufferFactory().wrap(responseStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}