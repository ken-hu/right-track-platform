package pers.ken.rt.gw.oauth.filter;

import lombok.AllArgsConstructor;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @ClassName: AuthenticationInfoFilter
 * @CreateTime: 2023/1/7 18:27
 * @Desc: 传递用户信息到下游服务
 * @Author Ken
 */
//@Component
@AllArgsConstructor
public class AuthenticationInfoFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 兼容permitAll()的情况 没有SecurityContext直接Return chain.filter()
//        return ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .map(Authentication::getPrincipal)
//                .defaultIfEmpty(AnonymousOauth2User.defaultUser())
//                .cast(DefaultOAuth2User.class)
//                .flatMap(principal -> {
//                    if (AnonymousOauth2User.isAnonymous(principal)) {
//                        return chain.filter(exchange);
//                    }
//                    AccountContext accountInfo = (AccountContext) Optional.ofNullable(principal.getAttribute(SecurityConstant.ACC_INFO)).orElse(new AccountContext());
//                    String userInfoJson = Jackson.toJsonString(accountInfo);
//                    byte[] jsonBytes = StringCompressorUtils.compress(userInfoJson);
//                    String encode = new String(Base64.getEncoder().encode(jsonBytes));
//                    ServerHttpRequest request = exchange.getRequest()
//                            .mutate()
//                            .header(HttpHeaderCons.ACC_INFO, encode)
//                            .header(HttpHeaderCons.ACC_ID, String.valueOf(Optional.ofNullable(accountInfo.getId()).orElse(-1)))
//                            .header(HttpHeaderCons.ACC_ENTERPRISE_ID, String.valueOf(Optional.ofNullable(accountInfo.getEnterpriseId()).orElse(-1)))
//                            .build();
//                    return chain.filter(exchange.mutate().request(request).build());
//                })
//                .doOnError(Throwable.class, throwable -> log.error("accept Throwable", throwable));
        return chain.filter(exchange);
    }
}
