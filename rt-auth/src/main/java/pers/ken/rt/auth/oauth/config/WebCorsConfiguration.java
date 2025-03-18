package pers.ken.rt.auth.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pers.ken.rt.auth.config.WebCorsProperties;

/**
 * @ClassName: CorsConfig
 * @Created: 2024/12/10 20:54
 * @Author ken
 */
@Configuration
@EnableConfigurationProperties(WebCorsProperties.class)
@Slf4j
public class WebCorsConfiguration {
    @Bean
    @ConditionalOnProperty(name = WebCorsProperties.ENABLED, havingValue = "true")
    public CorsConfigurationSource corsConfigurationSource(WebCorsProperties webCorsProperties) {
        log.info("Cross-domain custom configuration takes effect ");
        CorsConfiguration config = new CorsConfiguration();
        // 允许发送 Cookie [[1]]
        config.setAllowCredentials(webCorsProperties.getAllowCredentials());
        // 允许的前端域名
        config.setAllowedOriginPatterns(webCorsProperties.getAllowedOriginPatterns());
        // 允许所有头部
        config.addAllowedHeader(webCorsProperties.getAllowedHeader());
        // 允许所有方法（GET/POST/OPTIONS）
        config.addAllowedMethod(webCorsProperties.getAllowedMethod());
        // 预检请求缓存时间
        config.setMaxAge(webCorsProperties.getMaxAge());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 应用到所有路径
        source.registerCorsConfiguration(webCorsProperties.getBaseUrl(), config);
        return source;
    }
}
