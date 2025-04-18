package pers.ken.rt.gw.oauth.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName: CustomOAuth2SuccessHandler
 * @Created: 2025/3/21 14:02
 * @Author ken
 */
public class CustomOAuth2SuccessHandler implements ServerAuthenticationSuccessHandler, Consumer<List<ServerAuthenticationSuccessHandler>> {
    @Override
    public void accept(List<ServerAuthenticationSuccessHandler> serverAuthenticationSuccessHandlers) {

    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return null;
    }
}
