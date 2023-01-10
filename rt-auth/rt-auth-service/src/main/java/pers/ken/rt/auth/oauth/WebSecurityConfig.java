package pers.ken.rt.auth.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

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
    SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        http.authorizeHttpRequests(authorize ->
                        authorize
                                .antMatchers("/webjars/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                                .anyRequest().authenticated()
                )
                // 表单登录
                .formLogin(Customizer.withDefaults())
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .oauth2ResourceServer(configurer -> {
                    configurer
                            .authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler)
                            .jwt();
                });
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


}
