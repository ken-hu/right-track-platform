package pers.ken.rt.auth.oauth.support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.io.IOException;

/**
 * @ClassName: CustomAuthorizationRequestResolver
 * @Created: 2024/12/5 21:52
 * @Author ken
 */
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    public CustomAuthorizationRequestResolver(DefaultOAuth2AuthorizationRequestResolver defaultResolver) {
        this.defaultResolver = defaultResolver;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return handleInvalidClientRegistration(request, null);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        return handleInvalidClientRegistration(request, clientRegistrationId);
    }

    private OAuth2AuthorizationRequest handleInvalidClientRegistration(HttpServletRequest request, String clientRegistrationId) {
        try {
            return (clientRegistrationId == null)
                    ? defaultResolver.resolve(request)
                    : defaultResolver.resolve(request, clientRegistrationId);
        } catch (Exception ex) {
            // 响应 JSON 错误
            sendErrorResponse(request, "Invalid Client Registration ID: " + clientRegistrationId);
            return null; // 返回 null 阻止后续流程
        }
    }

    private void sendErrorResponse(HttpServletRequest request, String errorMessage) {
        HttpServletResponse response = (HttpServletResponse) request.getAttribute("javax.servlet.http.HttpServletResponse");
        try {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
