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
import org.springframework.util.CollectionUtils;
import pers.ken.rt.gw.conf.AuthorityProperties;

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
    private AuthorityProperties authorityProperties;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ServerAuthenticationEntryPoint authenticationEntryPoint,
                                                         ServerAccessDeniedHandler accessDeniedHandler) {
        http.csrf().disable()
                // authorize
                .authorizeExchange(exchange -> {
                    ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = exchange.pathMatchers(HttpMethod.OPTIONS).permitAll()
                            .pathMatchers("/auth/api/**").permitAll();
                    if (!CollectionUtils.isEmpty(authorityProperties.getWhiteList())) {
                        authorizeExchangeSpec.pathMatchers(authorityProperties.getWhiteList().toArray(new String[0])).permitAll();
                    }
                    authorizeExchangeSpec
                            .anyExchange()
                            .authenticated()
                    ;
                })
                // exception handler
                .exceptionHandling(handler -> {
                    handler.authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler);
                })
                // ResourceServer for jwt
                .oauth2ResourceServer(resourceServer -> {
                    resourceServer
                            .authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler)
                            .jwt();

                });
        return http.build();
    }
}

