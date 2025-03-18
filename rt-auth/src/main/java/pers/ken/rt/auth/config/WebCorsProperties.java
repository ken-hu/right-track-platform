package pers.ken.rt.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName: CorsProperties
 * @Created: 2025/3/18 15:33
 * @Author ken
 */
@Data
@ConfigurationProperties(prefix = WebCorsProperties.PREFIX)
public class WebCorsProperties {

    public static final String PREFIX = "rt.web.cors";
    public static final String ENABLED = PREFIX + ".enabled";

    private String baseUrl = "/**";
    private Boolean allowCredentials = true;
    private List<String> allowedOriginPatterns;
    private String allowedHeader = "*";
    private String allowedMethod = "*";
    private Long maxAge = 3600L;
    private Boolean enabled = false;
}
