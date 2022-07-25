package pers.ken.rt.gw.filter;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pers.ken.rt.iam.api.PolicyApi;
import pers.ken.rt.iam.dto.req.UserPoliciesReq;
import pers.ken.rt.iam.dto.resp.UserPoliciesResp;
import reactor.core.publisher.Mono;

/**
 * <code> AuthorityFilter </code>
 * <desc> AuthorityFilter </desc>
 * <b>Creation Time:</b> 2022/3/4 0:44.
 *
 * @author _Ken.Hu
 */
@Component
@AllArgsConstructor
public class ApiAccessManagementFilter implements GlobalFilter, Ordered {

    private PolicyApi policyApi;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取用户权限信息
        ServerHttpRequest request = exchange.getRequest();
        // one request (params + uri + methodValue)
        String uri = request.getURI().toString();
        String methodValue = request.getMethodValue();
        // api访问控制
        UserPoliciesResp userPoliciesResp = policyApi.listPolicies(request.getHeaders().getFirst("Authorization"),
                UserPoliciesReq.builder()
                        .resource(uri)
                        .action(methodValue)
                        .build());
        // set policy to header (JWT)

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
