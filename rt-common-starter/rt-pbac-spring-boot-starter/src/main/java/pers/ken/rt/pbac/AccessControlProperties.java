package pers.ken.rt.pbac;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <code> AccessControlProperties </code>
 * <desc> AccessControlProperties </desc>
 * <b>Creation Time:</b> 2022/8/2 11:57.
 *
 * @author Ken.Hu
 */
@ConfigurationProperties(prefix = AccessControlProperties.ACCESS_CONTROL_PREFIX)
@Data
public class AccessControlProperties {

    public static final String ACCESS_CONTROL_API_ENABLE = "access-control.api-auth.enabled";
    public static final String ACCESS_CONTROL_PREFIX = "access-control";
    public static final String ACCESS_CONTROL_DATA_ENABLE = "access-control.data-auth.enabled";
    private String policiesUri;
    private String serviceName = "";
    private String resourceCode = "";
    private ApiAuth apiAuth;
    private DataAuth dataAuth;

    @Data
    public static class ApiAuth {
        private boolean enabled;
    }

    @Data
    public static class DataAuth {
        private boolean enabled;
    }
}
