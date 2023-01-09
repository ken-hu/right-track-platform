package pers.ken.rt.auth.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.utils.Jackson;

import java.nio.charset.StandardCharsets;

import static pers.ken.rt.common.exception.ServiceCode.PERMISSION_NOT_ENOUGH;

/**
 * <name> WebSecurityConfig </name>
 * <desc> WebSecurityConfig </desc>
 * Creation Time: 2021/10/5 21:35.
 *
 * @author _Ken.Hu
 */
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                )
                // 表单登录
                .formLogin(Customizer.withDefaults())
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.error("Occur AccessDeniedException", accessDeniedException);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            response.getWriter().write(
                    Jackson.toJsonString(
                            PlatformError.builder()
                                    .code(PERMISSION_NOT_ENOUGH.getCode())
                                    .message(PERMISSION_NOT_ENOUGH.getMessage())
                                    .detail(accessDeniedException.getMessage())
                                    .requestId(request.getHeader(HttpHeaderCons.REQUEST_ID))
                                    .path(request.getRequestURI())
                                    .build())
            );

            response.getWriter().flush();
            response.getWriter().close();
        };
    }
}
