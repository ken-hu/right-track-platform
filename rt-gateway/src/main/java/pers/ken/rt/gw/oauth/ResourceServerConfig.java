package pers.ken.rt.gw.oauth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

/**
 * <code> ResourceServerConfig </code>
 * <desc> ResourceServerConfig </desc>
 * <b>Creation Time:</b> 2022/7/4 16:36.
 *
 * @author Ken.Hu
 */
@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class ResourceServerConfig {
    private final ServerAuthenticationEntryPoint serviceAuthenticationEntryPoint;
    private final ServerAccessDeniedHandler serviceAccessDeniedHandler;

    private final ReactiveAuthorizationManager<AuthorizationContext> policyAuthorizationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
                // authorize
                .authorizeExchange()
                .pathMatchers("/oauth/**").permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "*").permitAll()
                .pathMatchers("/**")
                .access(policyAuthorizationManager)
                .anyExchange()
                .authenticated()
                .and()
                // exception handler
                .exceptionHandling()
                .authenticationEntryPoint(serviceAuthenticationEntryPoint)
                .accessDeniedHandler(serviceAccessDeniedHandler)
                .and()
                // resource server config
                .oauth2ResourceServer()
                .opaqueToken()
                .introspectionClientCredentials("", "")
                .introspectionUri("")
                .introspector(new ReactiveOpaqueTokenIntrospector() {
                    @Override
                    public Mono<OAuth2AuthenticatedPrincipal> introspect(String token) {
                        return null;
                    }
                })
                .and()
                .authenticationEntryPoint(serviceAuthenticationEntryPoint)
                .accessDeniedHandler(serviceAccessDeniedHandler)
        ;
        return http.build();
    }
}
