package pers.ken.rt.gw.oauth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * <code> PolicyAuthorizationManager </code>
 * <desc> PolicyAuthorizationManager </desc>
 * <b>Creation Time:</b> 2022/7/28 15:19.
 *
 * @author Ken.Hu
 */
@Component
@AllArgsConstructor
public class PolicyAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final WebClient webClient;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication
                .filter(Authentication::isAuthenticated)
                .map(x -> {
                    boolean auth = isAuth();
                    return new AuthorizationDecision(auth);
                })
                .defaultIfEmpty(new AuthorizationDecision(true));
    }

    private boolean isAuth(){
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
        return true;
    }
}
