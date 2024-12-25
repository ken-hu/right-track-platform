package pers.ken.rt.starter.pbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ken.rt.starter.pbac.core.DynamicExpressionsProvider;
import pers.ken.rt.starter.pbac.core.InMemoryDynamicExpressionProvider;
import pers.ken.rt.starter.pbac.core.PermissionExceptionTranslation;
import pers.ken.rt.starter.pbac.core.PolicyProvider;
import pers.ken.rt.starter.pbac.exception.DefaultPermissionExceptionTranslation;
import pers.ken.rt.starter.pbac.permission.api.AccessManagerAspect;

/**
 * <code> AccessManagementConfiguration </code>
 * <desc> AccessManagementConfiguration </desc>
 * <b>Creation Time:</b> 2022/8/4 16:19.
 *
 * @author Ken.Hu
 */
@Configuration
@EnableConfigurationProperties(PbacProperties.class)
@ConditionalOnProperty(value = PbacProperties.ACCESS_CONTROL_API_ENABLE, havingValue = "true")
@Slf4j
public class ApiAccessManagerAutoConfiguration {

    /**
     * Default policy provider policy provider.
     *
     * @return the policy provider
     */
//    @Bean
//    @Lazy
//    @ConditionalOnMissingBean
//    public PolicyProvider defaultPolicyProvider() {
//        log.info("PbacAutoConfiguration defaultPolicyProvider init succeed");
//        return new InMemoryPolicyProvider();
//    }

    /**
     * Default dynamic expressions provider dynamic expressions provider.
     *
     * @return the dynamic expressions provider
     */
    @Bean
    @ConditionalOnMissingBean
    public DynamicExpressionsProvider defaultDynamicExpressionsProvider() {
        log.info("PbacAutoConfiguration defaultDynamicExpressionsProvider init succeed");
        return new InMemoryDynamicExpressionProvider();
    }

    /**
     * Permission exception translation permission exception translation.
     *
     * @return the permission exception translation
     */
    @Bean
    @ConditionalOnMissingBean
    public PermissionExceptionTranslation permissionExceptionTranslation() {
        log.info("PbacAutoConfiguration defaultPermissionExceptionTranslation init succeed");
        return new DefaultPermissionExceptionTranslation();
    }

    /**
     * Access management aspect access control aspect.
     *
     * @param pbacProperties                 the access control properties
     * @param provider                       the provider
     * @param dynamicExpressionsProvider     the dynamic expressions provider
     * @param permissionExceptionTranslation the permission exception translation
     * @return the access control aspect
     */
    @Bean
    @ConditionalOnMissingBean
    public AccessManagerAspect accessManagementAspect(PbacProperties pbacProperties,
                                                      PolicyProvider provider,
                                                      DynamicExpressionsProvider dynamicExpressionsProvider,
                                                      PermissionExceptionTranslation permissionExceptionTranslation) {
        log.info("PbacAutoConfiguration accessManagementAspect init succeed");
        return new AccessManagerAspect(pbacProperties, provider, permissionExceptionTranslation, dynamicExpressionsProvider);
    }
}
