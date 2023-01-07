package pers.ken.rt.gw.oauth.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.utils.Jackson;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static pers.ken.rt.common.exception.ServiceCode.AUTHENTICATION_FAILED;

/**
 * <code>ServiceAuthenticationEntryPoint</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/6/10 22:16.
 *
 * @author _Ken.Hu
 */
@Slf4j
@Component
public class Oauth2AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authenticationException) {
        log.error("Occur AuthenticationException", authenticationException);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String responseStr = Jackson.toJsonString(
                PlatformError.builder()
                        .code(AUTHENTICATION_FAILED.getCode())
                        .message(AUTHENTICATION_FAILED.getMessage())
                        .detail(authenticationException.getMessage())
                        .requestId(exchange.getRequest().getHeaders().getFirst(HttpHeaderCons.REQUEST_ID))
                        .path(exchange.getRequest().getPath().value())
                        .build()
        );
        DataBuffer buffer = response.bufferFactory().wrap(responseStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}