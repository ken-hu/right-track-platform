package pers.ken.rt.auth.oauth.support.password;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.*;

/**
 * @ClassName: OAuth2AuthorizationGrantAuthenticationToken
 * @Created: 2024/11/19 21:16
 * @Author ken
 */
@Getter
public class PasswordAuthenticationGrantToken extends OAuth2AuthorizationGrantAuthenticationToken {
    public static final AuthorizationGrantType PASSWORD_GRANT_TYPE = new AuthorizationGrantType("password");
    /**
     * 令牌申请访问范围
     */
    private final Set<String> scopes;

    public PasswordAuthenticationGrantToken(
            Authentication clientPrincipal,
            Set<String> scopes,
            Map<String, Object> additionalParameters) {
        super(PASSWORD_GRANT_TYPE, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
    }

    /**
     * 用户凭证(密码)
     */
    @Override
    public Object getCredentials() {
        return this.getAdditionalParameters().get(OAuth2ParameterNames.PASSWORD);
    }

    @Override
    public Object getPrincipal() {
        return this.getAdditionalParameters().get(OAuth2ParameterNames.USERNAME);
    }

    public String getClientId() {
        return Optional.ofNullable(((OAuth2ClientAuthenticationToken) super.getPrincipal()).getRegisteredClient()).map(RegisteredClient::getClientId).orElse(null);
    }

}
