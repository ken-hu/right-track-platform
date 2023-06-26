package pers.ken.rt.auth.infrastructure.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.domain.entity.aggregate.Policy;
import pers.ken.rt.auth.domain.repository.PolicyRepository;
import pers.ken.rt.auth.infrastructure.converter.PolicyConverter;
import pers.ken.rt.auth.infrastructure.repository.persistence.mapper.PolicyMapper;
import pers.ken.rt.auth.infrastructure.repository.persistence.po.PolicyPO;

import java.util.ArrayList;
import java.util.List;

/**
 * <code> PolicyDocumentServiceImpl </code>
 * <desc> PolicyDocumentServiceImpl </desc>
 * <b>Creation Time:</b> 2022/6/14 15:14.
 *
 * @author Ken.Hu
 */
@Component
@RequiredArgsConstructor
public class PolicyRepositoryImpl implements PolicyRepository {
    private final PolicyMapper policyMapper;

    @Override
    public void savePolicy(Policy policy) {
        PolicyPO po = PolicyConverter.INSTANCE.convert(policy);
        policyMapper.insert(po);
    }

    @Override
    public void delete(Long id) {
        policyMapper.deleteById(id);
    }

    @Override
    public List<Policy> findListByUserId(Long userId) {
        List<PolicyPO> policies = policyMapper.selectAllByUserId(userId);
        return PolicyConverter.INSTANCE.convert(policies);
    }

    @Override
    public List<Policy> findListByRoleIds(List<Long> roleIds) {
        return new ArrayList<>();
    }

    @Override
    public List<Policy> findListByGroupIds(List<Long> groupIds) {
        return null;
    }
}
