package pers.ken.rt.test.iam;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pers.ken.rt.iam.AccessManagementSupportConfiguration;
import pers.ken.rt.iam.internal.PolicyGetHandler;
import pers.ken.rt.iam.permission.access.ResourceConvertRegistry;
import pers.ken.rt.test.repository.PolicyRepository;

/**
 * <code> ResourceConvert </code>
 * <desc> ResourceConvert </desc>
 * <b>Creation Time:</b> 2022/8/10 16:00.
 *
 * @author Ken.Hu
 */
@Component
public class TestIAMConfiguration implements AccessManagementSupportConfiguration {
    @Override
    public void addResourceConvert(ResourceConvertRegistry registry) {
        registry.addConvert(new TestConvert());
        registry.addConvert(new CategoryConvert());
        AccessManagementSupportConfiguration.super.addResourceConvert(registry);
    }

    @Bean
    public PolicyGetHandler policyGetHandler(PolicyRepository policyRepository){
        return new TestPolicyHandler(policyRepository);
    }
}
