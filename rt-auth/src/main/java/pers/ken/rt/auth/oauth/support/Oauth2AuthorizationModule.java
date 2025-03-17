package pers.ken.rt.auth.oauth.support;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.oauth.support.mixin.*;

/**
 * @ClassName: Oauth2AuthorzationSeri
 * @Created: 2025/3/17 15:09
 * @Author ken
 */
@Component
public class Oauth2AuthorizationModule extends SimpleModule {

    public Oauth2AuthorizationModule() {
        super(Oauth2AuthorizationModule.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(OAuth2Authorization.class, OAuth2AuthorizationMixin.class);
        context.setMixInAnnotations(OAuth2AuthorizationCode.class, OAuth2AuthorizationCodeMixin.class);
        context.setMixInAnnotations(OAuth2Authorization.Token.class, TokenMixin.class);
        context.setMixInAnnotations(OAuth2AccessToken.class, OAuth2AccessTokenMixin.class);
        context.setMixInAnnotations(OAuth2RefreshToken.class, OAuth2RefreshTokenMixin.class);
        context.setMixInAnnotations(AuthorizationGrantType.class, AuthorizationGrantTypeMixin.class);
        context.setMixInAnnotations(OAuth2AccessToken.TokenType.class, TokenTypeMixin.class);
    }

}
