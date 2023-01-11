package pers.ken.rt.pbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import pers.ken.rt.pbac.internal.DefaultPolicyGetHandler;
import pers.ken.rt.pbac.internal.PolicyGetHandler;
import pers.ken.rt.pbac.permission.access.AccessControlAspect;
import pers.ken.rt.pbac.permission.access.AccessControlSupport;
import pers.ken.rt.pbac.permission.data.IDataProvider;
import pers.ken.rt.pbac.permission.data.InMemoryDataProvider;

/**
 * <code> AccessManagementConfiguration </code>
 * <desc> AccessManagementConfiguration </desc>
 * <b>Creation Time:</b> 2022/8/4 16:19.
 *
 * @author Ken.Hu
 */
@Import(AccessControlProperties.class)
@ConditionalOnProperty(value = AccessControlProperties.ACCESS_CONTROL_API_ENABLE)
@Slf4j
public class AccessControlAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = AccessControlProperties.ACCESS_CONTROL_DATA_ENABLE)
    public IDataProvider inMemoryDataProvider() {
        return new InMemoryDataProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    @ConditionalOnMissingBean
    public PolicyGetHandler policyGetHandler(RestTemplate restTemplate, AccessControlProperties accessControlProperties) {
        return new DefaultPolicyGetHandler(restTemplate, accessControlProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessControlSupport accessManagementSupport(PolicyGetHandler policyGetHandler, AccessControlProperties accessControlProperties) {
        return new AccessControlSupport(policyGetHandler, accessControlProperties);
    }

    @ConditionalOnMissingBean
    @Bean
    public AccessControlAspect accessManagementAspect(AccessControlSupport accessControlSupport) {
        return new AccessControlAspect(accessControlSupport);
    }
}
