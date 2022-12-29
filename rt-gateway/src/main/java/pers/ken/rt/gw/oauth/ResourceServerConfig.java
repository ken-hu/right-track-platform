package pers.ken.rt.gw.oauth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

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
    //    private final ReactiveAuthorizationManager<AuthorizationContext> policyAuthorizationManager;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ServerAuthenticationEntryPoint authenticationEntryPoint,
                                                         ServerAccessDeniedHandler accessDeniedHandler) {
        http.csrf().disable()
                // authorize
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "*").permitAll()
//                .access(policyAuthorizationManager)
                .anyExchange().authenticated()
                .and()
                // exception handler
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                // resource server config
                .oauth2ResourceServer()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .jwt();
        return http.build();
    }
}
