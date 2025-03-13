package pers.ken.rt.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.auth.dto.req.PolicyBindRequest;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.PolicyService;
import pers.ken.rt.auth.service.UserPolicyService;

import java.util.List;

/**
 * @ClassName: UserPolicyServiceImpl
 * @Created: 2025/3/7 12:04
 * @Author ken
 */
@Service
@RequiredArgsConstructor
public class UserPolicyServiceImpl implements UserPolicyService {
    private final PolicyService policyService;

    @Override
    public void bindUserPolicy(Integer userId, PolicyBindRequest request) {
        // 验证该用户是否在该租户下

        // 验证策略是否在该租户的授权范围内

        // 保存授权关系
    }

    @Override
    public List<Policy> listMyPolicy() {
        Integer userId = AccountContext.getUserId();
        return policyService.listPolicyByUser(userId);
    }
}
