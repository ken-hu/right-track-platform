package pers.ken.rt.gw.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: WebCorsConfiguration
 * @Created: 2025/3/18 14:57
 * @Author ken
 */
@Configuration
public class WebCorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("http://*.ken.com")
            .allowedMethods("*")
            .allowedHeaders("*")
            // 预检请求缓存时间
            .maxAge(3600L)
            // 允许携带凭证（如Cookies）
            .allowCredentials(true);
    }

}
