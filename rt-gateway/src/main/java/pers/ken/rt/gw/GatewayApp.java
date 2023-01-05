package pers.ken.rt.gw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * <name> GatewayApp </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 20:32.
 *
 * @author _Ken.Hu
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }

    @GetMapping(value = "/test")
    public Mono<Authentication> test(Authentication authentication) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(Objects::nonNull)
                .map(SecurityContext::getAuthentication);
    }
}
