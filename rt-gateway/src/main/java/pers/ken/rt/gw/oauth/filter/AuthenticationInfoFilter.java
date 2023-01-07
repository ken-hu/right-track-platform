package pers.ken.rt.gw.oauth.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import pers.ken.rt.common.cons.SecurityCons;
import pers.ken.rt.common.utils.Jackson;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @ClassName: AuthenticationInfoFilter
 * @CreateTime: 2023/1/7 18:27
 * @Desc: 传递用户信息到下游服务
 * @Author Ken
 */
@Component
public class AuthenticationInfoFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(Objects::nonNull)
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(DefaultOAuth2User.class)
                .flatMap(principal -> {
                    ServerHttpRequest request = exchange.getRequest();
                    if (Objects.nonNull(principal)) {
                        String userInfoJson = Jackson.toJsonString(principal);
                        String encode = new String(Base64.getEncoder().encode(userInfoJson.getBytes(StandardCharsets.UTF_8)));
                        request = exchange.getRequest().mutate().header(SecurityCons.USER_INFO, encode).build();
                    }
                    return chain.filter(exchange.mutate().request(request).build());
                });
    }
}
