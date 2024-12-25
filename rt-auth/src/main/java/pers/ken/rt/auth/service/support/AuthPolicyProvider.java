package pers.ken.rt.auth.service.support;

import lombok.RequiredArgsConstructor;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.PolicyService;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.starter.pbac.core.PolicyProvider;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.List;

/**
 * @ClassName: AuthPolicyProvider
 * @Created: 2024/12/9 20:00
 * @Author ken
 */
@RequiredArgsConstructor
public class AuthPolicyProvider implements PolicyProvider {
    private final PolicyService policyService;

    @Override
    public List<PolicyDocument> loadMyPolicies() {
        List<Policy> policies = policyService.listMyPolicies();
        return policies.stream().map(policy -> Jackson.fromJsonString(policy.getContent(), PolicyDocument.class)).toList();
    }
}
