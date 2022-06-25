package pers.ken.rt.gw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.common.iam.PolicyUtils;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * <code> AuthorityFilter </code>
 * <desc> AuthorityFilter </desc>
 * <b>Creation Time:</b> 2022/3/4 0:44.
 *
 * @author _Ken.Hu
 */
public class AuthorityActionFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String methodValue = request.getMethodValue();
        String actionCode = PolicyUtils.uriToActionCode("uc", methodValue, uri.toString());
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
