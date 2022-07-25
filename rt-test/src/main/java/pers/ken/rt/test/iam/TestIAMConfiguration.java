package pers.ken.rt.test.iam;

import org.springframework.stereotype.Component;
import pers.ken.rt.iam.AccessManagementSupportConfiguration;
import pers.ken.rt.iam.permission.access.ResourceConvertRegistry;

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
        AccessManagementSupportConfiguration.super.addResourceConvert(registry);
    }
}
