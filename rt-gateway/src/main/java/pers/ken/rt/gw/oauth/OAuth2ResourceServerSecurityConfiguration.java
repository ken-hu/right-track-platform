package pers.ken.rt.gw.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Name: Oauth2ResourceServerConfig
 * Create Time: 2022/12/29 17:58.
 *
 * @author Ken
 */
//@Configuration
//@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfiguration {
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //关闭CSRF,使用的是JWT，这里不需要csrf
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**")
                .permitAll()
                .antMatchers("/oauth2/**")
                .permitAll()
                .antMatchers("/test")
                .permitAll()
                .antMatchers("/rsa/getPublicKey")
                .permitAll()
                .antMatchers("swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**", "/v3/api-docs/**", "/v2/api-docs/**", "/**/v3/api-docs")
                .permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and()
//                .exceptionHandling()
//                // 处理未授权
//                .accessDeniedHandler(accessDeniedHandler)
//                // 处理未认证
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .and()
                .anonymous()
                .disable()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
//    }
}
