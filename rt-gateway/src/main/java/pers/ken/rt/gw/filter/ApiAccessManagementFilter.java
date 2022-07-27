package pers.ken.rt.gw.filter;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
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


    private WebClient webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取用户权限信息
        ServerHttpRequest request = exchange.getRequest();
        // one request (params + uri + methodValue)
        String uri = request.getURI().toString();
        String methodValue = request.getMethodValue();
        // api访问控制
        Mono<Object> toMono = webClient.post()
                .uri("lb://iam-service/users/1/policies")
                .body(BodyInserters.fromValue("{\n" +
                        "    \"resource\":\"data1\",\n" +
                        "    \"action\":\"read\"\n" +
                        "}"))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Object.class));

        // 不调用subscribe或者block是不会调用服务的
        Object block = toMono.block();

        System.out.println(block);
//        UserPoliciesResp userPoliciesResp = policyApi.listPolicies(request.getHeaders().getFirst("Authorization"),
//                UserPoliciesReq.builder()
//                        .resource(uri)
//                        .action(methodValue)
//                        .build());
        // set policy to header (JWT)

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
