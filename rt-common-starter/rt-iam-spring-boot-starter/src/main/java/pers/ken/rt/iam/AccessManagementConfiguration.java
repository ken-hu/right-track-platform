package pers.ken.rt.iam;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import pers.ken.rt.iam.internal.DefaultPolicyGetHandler;
import pers.ken.rt.iam.internal.PolicyGetHandler;
import pers.ken.rt.iam.permission.access.AccessManagementAspect;
import pers.ken.rt.iam.permission.access.AccessManagementSupport;
import pers.ken.rt.iam.permission.access.ResourceConvertRegistry;

/**
 * <code> AccessManagementConfiguration </code>
 * <desc> AccessManagementConfiguration </desc>
 * <b>Creation Time:</b> 2022/8/4 16:19.
 *
 * @author Ken.Hu
 */
@Import(AccessManagementProperties.class)
public class AccessManagementConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceConvertRegistry initRegistry(AccessManagementSupportConfiguration accessManagementSupportConfiguration) {
        ResourceConvertRegistry registry = new ResourceConvertRegistry();
        accessManagementSupportConfiguration.addResourceConvert(registry);
        return registry;
    }

    @Bean
    @ConditionalOnMissingBean
    public PolicyGetHandler policyGetHandler(RestTemplate restTemplate, AccessManagementProperties accessManagementProperties) {
        return new DefaultPolicyGetHandler(restTemplate, accessManagementProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessManagementSupport accessManagementSupport(PolicyGetHandler policyGetHandler, ResourceConvertRegistry registry, AccessManagementProperties accessManagementProperties) {
        return new AccessManagementSupport(policyGetHandler, registry, accessManagementProperties);
    }

    @ConditionalOnMissingBean
    @Bean
    public AccessManagementAspect accessManagementAspect(AccessManagementSupport accessManagementSupport) {
        return new AccessManagementAspect(accessManagementSupport);
    }

}
