package pers.ken.rt.uc.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * <name> WebSecurityConfig </name>
 * <desc> WebSecurityConfig </desc>
 * Creation Time: 2021/10/5 21:35.
 *
 * @author _Ken.Hu
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/rsa/getPublicKey")
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/webjars/**",
                        "/v3/api-docs/**", "/v2/api-docs/**")
        ;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭CSRF,使用的是JWT，这里不需要csrf
                .csrf().disable()
//                .requestMatchers().antMatchers("/test")
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/rsa/getPublicKey").permitAll()
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/webjars/**",
                        "/v3/api-docs/**", "/v2/api-docs/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                // 处理未授权
                .accessDeniedHandler(accessDeniedHandler)
                // 处理未认证
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
//                .httpBasic()
//                .and()
                .anonymous()
                .disable()
        ;
    }
}
