package pers.ken.rt.gw.oauth.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.common.model.ErrorResponse;
import pers.ken.rt.common.utils.Jackson;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: SecurityUtils
 * @Created: 2024/11/19 15:41
 * @Author ken
 */
@Slf4j
public class AuthorizationSupporter {

    private AuthorizationSupporter() {
    }


    /**
     * 认证与鉴权失败回调
     *
     * @param e 具体的异常信息
     */
    public static Mono<Void> exceptionHandler(ServerWebExchange exchange, Throwable e) {
        log.warn("AuthorizationServer exceptionHandler", e);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-cache");
        String responseStr = Jackson.toJsonString(
            ErrorResponse.builder()
                .code("access deny")
                .error("access deny")
                .build()
        );
        DataBuffer buffer = response.bufferFactory().wrap(responseStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }


    /**
     * 获取Oauth2框架返回的一些error_code和明细信息
     *
     * @param exception
     * @param oAuth2Error
     * @return
     */
    private static String getErrorDetailMessage(AuthenticationException exception, OAuth2Error oAuth2Error) {
        StringBuilder detailMessage = new StringBuilder();
        if (StringUtils.isNotBlank(exception.getMessage())) {
            detailMessage.append(exception.getMessage());
        } else {
            detailMessage.append("Authentication failed");
        }
        if (StringUtils.isNotBlank(oAuth2Error.getErrorCode())) {
            detailMessage.append(". ")
                .append(oAuth2Error.getErrorCode());
        }
        if (StringUtils.isNotBlank(oAuth2Error.getDescription())) {
            detailMessage.append(":")
                .append(oAuth2Error.getDescription());
        }
        return detailMessage.toString();
    }
}
