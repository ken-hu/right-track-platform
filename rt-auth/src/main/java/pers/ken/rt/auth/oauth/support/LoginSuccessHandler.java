package pers.ken.rt.auth.oauth.support;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import pers.ken.rt.auth.controller.resp.LoginSuccessResp;
import pers.ken.rt.common.utils.Jackson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: LoginSuccessHandler
 * @Created: 2024/11/20 21:27
 * @Author ken
 */
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final String loginPageUri;

    private final AuthenticationSuccessHandler authenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (UrlUtils.isAbsoluteUrl(this.loginPageUri)) {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(Jackson.toJsonString(new LoginSuccessResp("success")));
            response.getWriter().flush();
        } else {
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
