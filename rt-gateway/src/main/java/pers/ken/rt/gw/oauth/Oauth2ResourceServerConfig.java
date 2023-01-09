package pers.ken.rt.gw.oauth;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import pers.ken.rt.gw.oauth.filter.AuthenticationInfoFilter;
import pers.ken.rt.gw.oauth.filter.TokenHeaderFilter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
public class Oauth2ResourceServerConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ServerAuthenticationEntryPoint authenticationEntryPoint,
                                                         ServerAccessDeniedHandler accessDeniedHandler,
                                                         TokenHeaderFilter tokenHeaderFilter,
                                                         AuthenticationInfoFilter authenticationFilter,
                                                         PolicyAuthorizationManager policyAuthorizationManager) {
        http.addFilterBefore(tokenHeaderFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(authenticationFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .csrf().disable()
                // authorize
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange()
                .access(policyAuthorizationManager)
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
                //opaqueToken
                .opaqueToken()
                .introspector(userInfoTokenIntrospector());
        return http.build();
    }

    @Bean
    public ReactiveOpaqueTokenIntrospector userInfoTokenIntrospector() {
        return Oauth2ResourceServerConfig::introspect;
    }

    private static Mono<OAuth2AuthenticatedPrincipal> introspect(String token) {
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        Map<String, Object> attributes = Maps.newLinkedHashMap();
        attributes.put("key", "val");
        attributes.put("user_name", "test");
        String nameAttributeKey = "user_name";
        return Mono.just(new DefaultOAuth2User(authorities, attributes, nameAttributeKey));
    }
}

