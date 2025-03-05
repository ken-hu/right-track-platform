package pers.ken.rt.auth.oauth.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
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
                if (e instanceof InvalidBearerTokenException exDetail) {
                    String errorDetailMessage = getErrorDetailMessage(exDetail, exDetail.getError());
                    errorResponse = ErrorResponse.of(ErrorCode.TOKEN_INVALID, errorDetailMessage);
                } else if (e instanceof InsufficientAuthenticationException) {
                    errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED, e.getMessage());
                } else if (e instanceof OAuth2AuthorizationCodeRequestAuthenticationException exDetail) {
                    String errorDetailMessage = getErrorDetailMessage(exDetail, exDetail.getError());
                    errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED, errorDetailMessage);
                } else if (e instanceof OAuth2AuthenticationException exDetail) {
                    String errorDetailMessage = getErrorDetailMessage(exDetail, exDetail.getError());
                    errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED, errorDetailMessage);
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
