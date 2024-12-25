package pers.ken.rt.auth.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pers.ken.rt.auth.oauth.model.SecurityConstant;
import pers.ken.rt.auth.oauth.support.LoginFailureHandler;
import pers.ken.rt.auth.oauth.support.LoginSuccessHandler;
import pers.ken.rt.auth.oauth.utils.AuthorizationSupporter;

/**
 * <name> WebSecurityConfig </name>
 * <desc> WebSecurityConfig </desc>
 * Creation Time: 2021/10/5 21:35.
 *
 * @author _Ken.Hu
 */
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@Configuration
public class WebSecurityConfig {

    //    private static final String LOGIN_URL = "http://127.0.0.1:5173/login";
    private static final String LOGIN_URL = "/login";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(SecurityConstant.WHITE_LIST)
                                .permitAll()
                                .anyRequest()
                                //如果要基于security框架来自定义 则需要实现AuthorizationManager接口 在authorizeHttpRequests下只能 implements AuthorizationManager<MethodInvocation> authorize.access(new PolicyAccessDecisionRequestManager())
                                //需要基于MethodInvocation的需要额外新增一个filter 如authorizeHttpRequests.addFilterBefore(new PolicyAccessDecisionMethodInvokeManager(), FilterSecurityInterceptor.class);
                                .authenticated()
                )
                // 表单登录
//                .formLogin(Customizer.withDefaults())
                .formLogin(formLogin -> {
                    formLogin
//                            .loginPage(LOGIN_URL)
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .successHandler(new LoginSuccessHandler(LOGIN_URL))
                            .failureHandler(new LoginFailureHandler(LOGIN_URL))
                    ;
                })
                .oauth2ResourceServer(configurer -> {
                    configurer
                            .jwt(Customizer.withDefaults())
                            .authenticationEntryPoint(AuthorizationSupporter::exceptionHandler)
                            .accessDeniedHandler(AuthorizationSupporter::exceptionHandler)
                    ;
                })
                // 联合登录配置
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                // 如果不配置loginPage属性则使用spring oauth2 默认的登录页面
                                .loginPage(LOGIN_URL)
                                .successHandler(new LoginSuccessHandler(LOGIN_URL))
                                .failureHandler(new LoginFailureHandler(LOGIN_URL))
                )
//                .exceptionHandling(exceptions ->
//                        exceptions
//                                .authenticationEntryPoint(AuthorizationSupporter::exceptionHandler)
//                                .accessDeniedHandler(AuthorizationSupporter::exceptionHandler)
//
//                )
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
