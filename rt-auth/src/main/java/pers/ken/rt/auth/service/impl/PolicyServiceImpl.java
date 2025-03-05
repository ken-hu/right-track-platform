package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.dto.req.AssignPoliciesRequest;
import pers.ken.rt.auth.dto.req.PolicyListRequest;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.mapper.PolicyMapper;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.PolicyService;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【policy】的数据库操作Service实现
 * @createDate 2024-12-07 15:19:39
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy>
        implements PolicyService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUserPolicy(Integer userId, AssignPoliciesRequest request) {
        baseMapper.deleteUserPolicyRelByPolicyIds(userId, request.getPolicyIds());
    }

    @Override
    public List<Policy> listMyPolicies() {
        Integer userId = AccountContext.getUserId();
        // user's policies + user-group's policies + role's policy
        return baseMapper.selectPoliciesByUser(userId);
    }

    @Override
    public Page<Policy> listPolicies(PolicyListRequest request) {
        return baseMapper.selectPage(Pages.toPage(request),
            Wrappers.lambdaQuery(Policy.class)
                .eq(Policy::getAppCode, request.getApplicationCode())
        );
    }


    @Override
    public void removeUserGroupPolicy(Integer userGroupId, AssignPoliciesRequest request) {
        request.getPolicyIds().forEach(policyId -> {
//            baseMapper.
        });
    }

}




