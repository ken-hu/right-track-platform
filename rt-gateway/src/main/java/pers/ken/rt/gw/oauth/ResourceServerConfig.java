package pers.ken.rt.gw.oauth;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import pers.ken.rt.common.exception.ServiceCode;
import pers.ken.rt.common.model.PlatformError;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <name> AuthResourceConfig </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 21:00.
 *
 * @author _Ken.Hu
 */
@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
@Slf4j
public class ResourceServerConfig {

    private Oauth2Properties oauth2Properties;
    private ResourceServerManager resourceServerManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // authorize
        http.authorizeExchange()
                .pathMatchers(oauth2Properties.getWhiteListUrls().toArray(new String[0])).permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "*").permitAll()
                .anyExchange()
                .access(resourceServerManager)
                .and()
                // exception handler
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .csrf().disable();

        http.oauth2ResourceServer()
                .jwt()
                // get public key from remote
                .jwkSetUri("http://localhost:38081/rsa/getPublicKey")
                // get public key from local
//                .publicKey(null)
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                .and()
                // 处理未授权
                .accessDeniedHandler(accessDeniedHandler())
                // 处理未认证
                .authenticationEntryPoint(authenticationEntryPoint())

        ;
        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.JWT_AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    /**
     * 自定义未授权响应
     */
    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    log.warn("ServerAccessDeniedHandler Exception ", denied);
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    headerSet(response);
                    String body = JSON.toJSONString(new PlatformError(ServiceCode.PERMISSION_NOT_ENOUGH,
                            "Please check your permission"));
                    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer));
                });
    }


    /**
     * token无效或者已过期自定义响应
     */
    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, e) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    log.warn("ServerAuthenticationEntryPoint Exception ", e);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    headerSet(response);
                    String body = JSON.toJSONString(new PlatformError(ServiceCode.TOKEN_INVALID,
                            "Please check your token"));
                    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer));
                });
    }


    private void headerSet(ServerHttpResponse response) {
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
    }

}
