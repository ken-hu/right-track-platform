package pers.ken.rt.gw.oauth;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pers.ken.rt.auth.dto.resp.PolicyResp;
import pers.ken.rt.common.model.PlatformResult;
import pers.ken.rt.common.utils.Jackson;
import reactor.core.publisher.Mono;

/**
 * <code> PolicyAuthorizationManager </code>
 * <desc> PolicyAuthorizationManager </desc>
 * <b>Creation Time:</b> 2022/7/28 15:19.
 *
 * @author Ken.Hu
 */
@Slf4j
@Component
@AllArgsConstructor
public class PolicyAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final WebClient webClient;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMap(info -> getAuthorizationDecision(info, authorizationContext))
                .defaultIfEmpty(new AuthorizationDecision(true));
    }

    private Mono<AuthorizationDecision> getAuthorizationDecision(Authentication authentication, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        Mono<PlatformResult<PolicyResp>> response = getResponse();
        return response.map(body -> {
            Boolean isPermit = Boolean.TRUE;
            PolicyResp data = body.getData();
            // check for permission
            return new AuthorizationDecision(isPermit);
        });
    }

    private Mono<PlatformResult<PolicyResp>> getResponse() {
        return webClient.get()
                .uri("http://localhost:38081/users/policies", "1")
                .header(HttpHeaders.AUTHORIZATION,
                        "Bearer eyJ0b2tlbl90eXBlIjoiYWNjZXNzX3Rva2VuIiwiYWxnIjoiUlMyNTYiLCJraWQiOiJjZGRhNTJjMi1iZDkyLTQzYzgtYmI2OC1kMDYzMDgzN2IzZDMifQ.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY3MzMzMzU5OCwidXNlcl9pbmZvIjoidGVzdCIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MzgwODEiLCJleHAiOjE2NzMzMzM4OTgsImlhdCI6MTY3MzMzMzU5OH0.MDjPW7vuRIE8WierCyJaHRqwB-aV4tnME_UgjkTmscVtK3090kAdaWo6Cftmle182oRrcNzDkpk74ZSJEPjYS_nx-j6CRXQ4eXbSWqpcOd79FZ1ZrD3Ub5jgSkkwA9LdItyoFjVA6GKZWc5vs-X6jS-gaB87QRQg7PJ-4LKj8OT0K5BQBdPbuTxs7627RMHJiBXN-vTQQkql2hlkOFYdgiO4s6gU-ogil3n6jgxrKBvx93I8VbvViav_XyPy_DDD4z6dV2FEO-3s4QcowoWWhF0lFYP1ZCEnJca5A41_ixFF3_rUPjVfV3g2RsYU3gZVBwjBGmtSdAzefB7pcfL7Ug")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    log.info("response:{}", json);
                    return Jackson.fromJsonString(json, new TypeReference<PlatformResult<PolicyResp>>() {
                    });
                })
                .doOnError(throwable -> {
                    throw new RuntimeException("Get user policies failed", throwable);
                });
    }
}
