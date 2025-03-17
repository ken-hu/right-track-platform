package pers.ken.rt.auth.oauth.support;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName: LoginSuccessHandler
 * @Created: 2024/11/20 21:27
 * @Author ken
 */
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // 从请求中获取客户端信息
//        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
//        String clientId = oauth2Token.getAuthorizedClientRegistrationId();
//
//        // 根据 clientId 获取对应的跳转 URL
//        ClientRegistration registrationInfo = clientRegistrationRepository.findByRegistrationId(clientId);
//        String redirectUri = registrationInfo.getRedirectUri();
//        String[] split = StringUtils.split(redirectUri, ",");
//        // 执行跳转
//        if (ArrayUtils.isNotEmpty(split)) {
//            response.sendRedirect(split[0]);
//        }else {
//            response.sendRedirect("https://www.bing.com");
//        }
    }
}
