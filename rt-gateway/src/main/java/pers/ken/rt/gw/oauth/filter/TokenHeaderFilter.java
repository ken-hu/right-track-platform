package pers.ken.rt.gw.oauth.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import pers.ken.rt.common.cons.SecurityConstant;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Name: TokenHeaderFilter
 * @CreateTime: 2023/1/7 17:04.
 * @Desc: 处理非标准的Token位置存放的情况
 * 自定义Token的位置获取 并放到标准的AUTHORIZATION(Header)
 * 不需要可以去除 org.springframework.security.config.web.server.ServerHttpSecurity#addFilterBefore(org.springframework.web.server.WebFilter, org.springframework.security.config.web.server.SecurityWebFiltersOrder)
 *
 * @Author Ken
 */
@Slf4j
@Component
public class TokenHeaderFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpCookie tokenInfo = exchange.getRequest().getCookies().getFirst(SecurityConstant.CUSTOM_TOKEN);
        String headerBearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        ServerHttpRequest request = exchange.getRequest();
        if (Objects.nonNull(tokenInfo) && Objects.isNull(headerBearerToken)) {
            request = exchange.getRequest().mutate()
                    .header(HttpHeaders.AUTHORIZATION, SecurityConstant.BEARER + tokenInfo.getValue())
                    .build();
        }
        return chain.filter(exchange.mutate().request(request).build());
    }
}
