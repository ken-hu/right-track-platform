package pers.ken.rt.starter.pbac;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <code> AccessControlProperties </code>
 * <desc> AccessControlProperties </desc>
 * <b>Creation Time:</b> 2022/8/2 11:57.
 *
 * @author Ken.Hu
 */
@Data
@ConfigurationProperties(prefix = PbacProperties.ACCESS_CONTROL_PREFIX)
public class PbacProperties {

    public static final String ACCESS_CONTROL_PREFIX = "rt-pbac";
    public static final String ACCESS_CONTROL_API_ENABLE = "rt-pbac.api-auth.enabled";
    public static final String ACCESS_CONTROL_API_CHECK_ENABLE = "rt-pbac.api-auth.check.enabled";
    public static final String ACCESS_CONTROL_DATA_ENABLE = "rt-pbac.data-auth.enabled";

    private String platform = "rt";
    private String service = "default";
    private boolean cacheEnabled = Boolean.FALSE;
    private boolean checkEnabled = false;
    private ApiAuth apiAuth;
    private DataAuth dataAuth;

    @Data
    public static class ApiAuth {
        private boolean enabled = true;
    }

    @Data
    public static class DataAuth {
        private boolean enabled = false;
    }
}
