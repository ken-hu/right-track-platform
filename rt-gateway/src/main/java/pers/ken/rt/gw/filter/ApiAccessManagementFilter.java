package pers.ken.rt.gw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
//@Component
//@AllArgsConstructor
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
//        Mono<String> exchange1 =
                webClient.post()
                .uri("http://localhost:38082/users/1/policies")
//                .uri("lb://iam-service/users/1/policies")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromObject("{\n" +
                        "    \"resource\":\"data1\",\n" +
                        "    \"action\":\"read\"\n" +
                        "}"))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(System.out::println)
                ;
//.flatMap(res -> {
//            System.out.println("---------------");
//
//            System.out.println(res);
//            return Mono.just(res);
//        });
        // 不调用subscribe或者block是不会调用服务的

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
