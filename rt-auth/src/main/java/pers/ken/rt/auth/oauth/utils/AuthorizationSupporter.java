package pers.ken.rt.auth.oauth.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import pers.ken.rt.common.exception.ErrorCode;
import pers.ken.rt.common.model.ErrorResponse;
import pers.ken.rt.common.utils.Jackson;

import java.io.IOException;
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
     * @param request  当前请求
     * @param response 当前响应
     * @param e        具体的异常信息
     */
    public static void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        log.warn("AuthorizationServer exceptionHandler", e);
        try {
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED, e.getMessage());
            if (e instanceof AccessDeniedException) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                errorResponse = ErrorResponse.of(ErrorCode.ACCESS_DENY, e.getMessage());
            }

            if (e instanceof AuthenticationException) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                if (e instanceof InvalidBearerTokenException) {
                    errorResponse = ErrorResponse.of(ErrorCode.TOKEN_INVALID, e.getMessage());
                } else if (e instanceof InsufficientAuthenticationException) {
                    errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED, e.getMessage());
                } else {
                    errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED, e.getMessage());
                }
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(Jackson.toJsonString(errorResponse));
            response.getWriter().flush();
        } catch (IOException ex) {
            log.error("Security Utils write to response failed.", e);
        }
    }
}
