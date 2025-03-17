package pers.ken.rt.auth.oauth.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pers.ken.rt.auth.dto.resp.LoginSuccessResponse;
import pers.ken.rt.auth.oauth.model.SecurityConstant;
import pers.ken.rt.auth.oauth.support.LoginFailureJsonHandler;
import pers.ken.rt.auth.oauth.utils.AuthorizationSupporter;
import pers.ken.rt.common.utils.Jackson;

import java.nio.charset.StandardCharsets;

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
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
            .formLogin(formLogin -> {
                formLogin
//                    .loginPage("http://uc.ken.com/login.html")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler((request, response, authentication) -> {
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.getWriter().write(Jackson.toJsonString(new LoginSuccessResponse("Login success")));
                        response.getWriter().flush();
                    })
                    .failureHandler(new LoginFailureJsonHandler());
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
//                    .loginPage("http://frontend:8080/login")
                        // 如果不配置loginPage属性则使用spring oauth2 默认的登录页面
                        .defaultSuccessUrl("http://app.ken.com/callback.html")
//                        .successHandler(new LoginSuccessHandler(clientRegistrationRepository))
                        .failureHandler(new LoginFailureJsonHandler())
            );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许发送 Cookie [[1]]
        config.setAllowCredentials(true);
        // 允许的前端域名
        config.setAllowedOriginPatterns(Lists.newArrayList("*.ken.com"));
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
