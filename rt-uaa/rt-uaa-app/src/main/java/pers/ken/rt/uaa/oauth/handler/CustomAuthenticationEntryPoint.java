package pers.ken.rt.uaa.oauth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.exception.ServiceCode;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.utils.Jackson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <name> CustomAuthenticationEntryPoint </name>
 * <desc> CustomAuthenticationEntryPoint </desc>
 * Creation Time: 2021/10/7 21:07.
 *
 * @author _Ken.Hu
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Occur AuthenticationException,Detail is", authException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.getWriter().write(
                Jackson.toJsonString(
                        new PlatformError(ServiceCode.TOKEN_INVALID.getCode(),
                                "Please check your token",
                                request.getHeader(HttpHeaderCons.REQUEST_ID),
                                request.getRequestURI()))
        );
        response.getWriter().flush();
    }

}
