package pers.ken.rt.auth.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pers.ken.rt.auth.application.assembler.PolicyAssembler;
import pers.ken.rt.auth.application.service.PolicyAppService;
import pers.ken.rt.auth.domain.entity.aggregate.Policy;
import pers.ken.rt.auth.domain.repository.PolicyRepository;
import pers.ken.rt.auth.interfaces.dto.req.PolicySaveRequest;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyItemResp;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyResp;

import java.util.List;

/**
 * @author Ken
 * @className: PolicyAppServiceImpl
 * @createdTime: 2023/3/9 16:14
 * @desc:
 */
@Service
@AllArgsConstructor
public class PolicyAppServiceImpl implements PolicyAppService {
    private final PolicyRepository policyRepository;

    @Override
    public List<PolicyResp> userPolicies(Long userId) {
        List<Policy> policies = policyRepository.findListByUserId(userId);
        return PolicyAssembler.INSTANCE.convert(policies);
    }

    @Override
    public List<PolicyItemResp> userPolicyItems(Long userId) {
        List<Policy> policies = policyRepository.findListByUserId(userId);
        return PolicyAssembler.INSTANCE.toPolicyItem(policies);
    }

    @Override
    public void create(PolicySaveRequest saveRequest) {
        Policy policy = PolicyAssembler.INSTANCE.convert(saveRequest);
        policyRepository.savePolicy(policy);
    }

    @Override
    public void update(Long id, PolicySaveRequest saveRequest) {
        Policy policy = PolicyAssembler.INSTANCE.convert(saveRequest);
        Assert.notNull(policy.getId(), "Policy not exists");
        policyRepository.savePolicy(policy);
    }

    @Override
    public void delete(Long id) {

    }
}
