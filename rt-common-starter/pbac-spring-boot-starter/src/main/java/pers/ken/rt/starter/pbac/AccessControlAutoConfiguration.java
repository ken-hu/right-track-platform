package pers.ken.rt.starter.pbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ken.rt.starter.pbac.exception.DefaultPermissionExceptionTranslation;
import pers.ken.rt.starter.pbac.internal.InMemoryPolicyProvider;
import pers.ken.rt.starter.pbac.internal.PermissionExceptionTranslation;
import pers.ken.rt.starter.pbac.internal.PolicyProvider;
import pers.ken.rt.starter.pbac.permission.access.AccessControlAspect;

/**
 * <code> AccessManagementConfiguration </code>
 * <desc> AccessManagementConfiguration </desc>
 * <b>Creation Time:</b> 2022/8/4 16:19.
 *
 * @author Ken.Hu
 */
@Configuration
@EnableConfigurationProperties(AccessControlProperties.class)
@ConditionalOnProperty(value = AccessControlProperties.ACCESS_CONTROL_API_ENABLE, havingValue = "true")
@Slf4j
public class AccessControlAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PolicyProvider policyGetHandler() {
        return new InMemoryPolicyProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public PermissionExceptionTranslation permissionExceptionTranslation() {
        return new DefaultPermissionExceptionTranslation();
    }

    @ConditionalOnMissingBean
    @Bean
    public AccessControlAspect accessManagementAspect(AccessControlProperties accessControlProperties,
                                                      PolicyProvider provider,
                                                      PermissionExceptionTranslation permissionExceptionTranslation) {
        return new AccessControlAspect(accessControlProperties, provider, permissionExceptionTranslation);
    }
}
