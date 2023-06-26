package pers.ken.rt.auth.infrastructure.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.utils.Jackson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static pers.ken.rt.common.exception.ServiceCode.TOKEN_INVALID;

/**
 * @ClassName: Oauth2AuthenticationEntryPoint
 * @CreatedTime: 2023/1/10 10:54
 * @Desc:
 * @Author Ken
 */
@Slf4j
@Component
public class Oauth2AuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Occur AuthenticationException", authException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.getWriter().write(
                Jackson.toJsonString(
                        PlatformError.builder()
                                .code(TOKEN_INVALID.getCode())
                                .message(TOKEN_INVALID.getMessage())
                                .detail(authException.getMessage())
                                .requestId(request.getHeader(HttpHeaderCons.REQUEST_ID))
                                .path(request.getRequestURI())
                                .build())
        );
        response.getWriter().flush();
        response.getWriter().close();
    }
}
