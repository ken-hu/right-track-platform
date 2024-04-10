package pers.ken.rt.starter.pbac.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: InMemoryPolicyProvider
 * @Created: 2023/10/31 18:08
 * @Desc:
 * @Author Ken
 */
public class InMemoryPolicyProvider implements PolicyProvider {

    @Override
    public List<PolicyDocument> loadPolicies() {
        return new ArrayList<>();
    }
}
