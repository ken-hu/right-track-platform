package pers.ken.rt.gw.oauth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
    private final ServiceAuthenticationEntryPoint serviceAuthenticationEntryPoint;
    private final ServiceAccessDeniedHandler serviceAccessDeniedHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
                // authorize
                .authorizeExchange()
                .pathMatchers("/oauth/**").permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "*").permitAll()
                .anyExchange()
                .authenticated()
                .and()
                // exception handler
                .exceptionHandling()
                .accessDeniedHandler(serviceAccessDeniedHandler)
                .authenticationEntryPoint(serviceAuthenticationEntryPoint)
                .and()
                // resource server config
                .oauth2ResourceServer()
                .opaqueToken()
                .introspectionClientCredentials("", "")
                .introspectionUri("")
        ;
        return http.build();
    }
}
