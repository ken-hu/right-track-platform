package pers.ken.rt.auth.infrastructure.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.utils.Jackson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static pers.ken.rt.common.exception.ServiceCode.PERMISSION_NOT_ENOUGH;

/**
 * @ClassName: Oauth2AccessDeniedHandler
 * @CreatedTime: 2023/1/10 10:53
 * @Desc:
 * @Author Ken
 */
@Slf4j
@Component
public class Oauth2AccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Occur AccessDeniedException", accessDeniedException);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.getWriter().write(
                Jackson.toJsonString(
                        PlatformError.builder()
                                .code(PERMISSION_NOT_ENOUGH.getCode())
                                .message(PERMISSION_NOT_ENOUGH.getMessage())
                                .detail(accessDeniedException.getMessage())
                                .requestId(request.getHeader(HttpHeaderCons.REQUEST_ID))
                                .path(request.getRequestURI())
                                .build())
        );

        response.getWriter().flush();
        response.getWriter().close();
    }
}
