package pers.ken.rt.gw.oauth;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import pers.ken.rt.gw.oauth.support.AuthorizationSupporter;
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
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            // .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 服务安全认证
            .authorizeExchange(exchange -> {
                exchange.pathMatchers("/business-redirect").permitAll()
                    .anyExchange()
                    .authenticated()
                ;
            })
            // 开启OAuth2登录
//            .oauth2Login(Customizer.withDefaults())
            // 资源服务相关拦截
            .oauth2ResourceServer(resourceServer -> {
                resourceServer
//                            .authenticationEntryPoint(authenticationEntryPoint)
//                            .accessDeniedHandler(accessDeniedHandler)
                    .jwt(jwt -> jwt
                        // 请求中携带token访问时会触发该解析器适配器
                        .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                    )
                    .authenticationEntryPoint(AuthorizationSupporter::exceptionHandler)
                    .accessDeniedHandler(AuthorizationSupporter::exceptionHandler);

            });
        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        // 允许发送 Cookie [[1]]
        config.setAllowCredentials(true);
        // 允许的前端域名
        config.setAllowedOriginPatterns(Lists.newArrayList("http://uc.ken.com", "http://app.ken.com"));
        // 允许所有头部
        config.addAllowedHeader("*");
        // 允许所有方法（GET/POST/OPTIONS）
        config.addAllowedMethod("*");
        // 预检请求缓存时间
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 应用到所有路径
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    /**
     * 自定义jwt解析器，设置解析出来的权限信息的前缀与在jwt中的key
     *
     * @return jwt解析器适配器 ReactiveJwtAuthenticationConverterAdapter
     */
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置解析权限信息的前缀，设置为空是去掉前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        // 设置权限信息在jwt claims中的key
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}

