package pers.ken.rt.gw.oauth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMap(authen -> getAuthorizationDecision())
                .defaultIfEmpty(new AuthorizationDecision(true));
    }

    private Mono<AuthorizationDecision> getAuthorizationDecision() {
        return Mono.just(new AuthorizationDecision(isAuth()));
    }

    private boolean isAuth() {
        getResponse();
        return true;
    }

    private void getResponse() {
        webClient.get()
                .uri("http://localhost:38081/users/{id}/policies", "1")
                .header(HttpHeaders.AUTHORIZATION,
                        "Bearer eyJ0b2tlbl90eXBlIjoiYWNjZXNzX3Rva2VuIiwiYWxnIjoiUlMyNTYiLCJraWQiOiIxNWJlODY2MS00NDBmLTRiZjEtOGI2Yi0wZTQzZjIwYmYxYmEifQ.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY3MzI1OTIwMywidXNlcl9pbmZvIjoidGVzdCIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MzgwODEiLCJleHAiOjE2NzMyNTk1MDMsImlhdCI6MTY3MzI1OTIwM30.bc-whZoqSvkcBPlIQOtah3YLU98Nc5cFn4yY3hcGLhKzCMT-qhENW0kPC1UPKtbLDL1vCrLhPb0POSlNdqcPH-1aGSjlusyZr4Y0tWV8bW4wsVhFKR-GGfSQLMwmrrSb2GfyMHDn-djgOwpBjdZXvbXJPIQCMeHuO_hc18ApjFiB9kP5RTH0Nzt-8Tdo6ukerCtXrBZklJfAoThI0oxH0Vr9HoVLGfzCnNjr8ftlnNFfVhk9-cFPXyhGFZ6NUDxxC8vL1KoLrX20klkqthZA65flKo-1JTiW0IDwHzrhoB6rrMpUFUpgFDuMqeG5WJG30KXr3VOchLS_uiu89lIsLw")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(System.out::println);
    }
}
