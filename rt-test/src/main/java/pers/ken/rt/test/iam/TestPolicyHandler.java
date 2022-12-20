package pers.ken.rt.test.iam;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.iam.internal.Policy;
import pers.ken.rt.iam.internal.PolicyGetHandler;
import pers.ken.rt.test.entity.AuthPolicy;
import pers.ken.rt.test.repository.PolicyRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Creation Time: 2022/11/15 11:10.
 *
 * @author _Ken.Hu
 */
@Component
@AllArgsConstructor
public class TestPolicyHandler implements PolicyGetHandler {

    private PolicyRepository policyRepository;

    @Override
    public List<Policy> userPolicies() {
        List<AuthPolicy> policies = policyRepository.getAuthPolicyByUserId(1L);
       return policies.stream()
               .map(p-> Jackson.fromJsonString(p.getPolicyDocument(), new TypeReference<Policy>() {
               })).collect(Collectors.toList());
    }
}
