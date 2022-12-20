package pers.ken.rt.iam;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <code> IamConfig </code>
 * <desc> IamConfig </desc>
 * <b>Creation Time:</b> 2022/8/2 11:57.
 *
 * @author Ken.Hu
 */
@ConfigurationProperties(prefix = "api-access.auth")
@Data
public class AccessManagementProperties {
    private String serviceName;
    private String resourceCode;
    private boolean enabled;
    private String pathPatterns;
    private String excludePathPatterns;
    private String policiesUri;
}
