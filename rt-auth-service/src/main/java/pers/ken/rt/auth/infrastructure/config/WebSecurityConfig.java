package pers.ken.rt.auth.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * <name> WebSecurityConfig </name>
 * <desc> WebSecurityConfig </desc>
 * Creation Time: 2021/10/5 21:35.
 *
 * @author _Ken.Hu
 */
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler,
                                            AuthenticationEntryPoint authenticationEntryPoint) throws Exception {

        http.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-resources/**",
                                        "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                // 表单登录
                .formLogin(Customizer.withDefaults())
                .oauth2ResourceServer(configurer -> {
                    configurer
                            .authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler)
                            .jwt();
                })
        ;
        return http.build();
    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Bean
    UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public Advisor customAuthorize(PolicyAuthorizationManager rules) {
//        AnnotationMatchingPointcut annotationMatchingPointcut = new AnnotationMatchingPointcut(AccessControl.class, AccessControl.class);
//        AuthorizationManagerBeforeMethodInterceptor interceptor = new AuthorizationManagerBeforeMethodInterceptor(
//                annotationMatchingPointcut, rules);
//        interceptor.setOrder(AuthorizationInterceptorsOrder.PRE_AUTHORIZE.getOrder() + 1);
//        return interceptor;
//    }
}
