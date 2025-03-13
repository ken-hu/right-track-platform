package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.dto.req.ListPolicyRequest;
import pers.ken.rt.auth.dto.req.PolicyBindRequest;
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
    public void removeUserPolicy(Integer userId, PolicyBindRequest request) {
        baseMapper.deleteUserPolicyRelByPolicyIds(userId, request.getPolicyIds());
    }

    @Override
    public List<Policy> listPolicyByUser(Integer userId) {
        // user's policies + user-group's policies + role's policy
        return baseMapper.selectPoliciesByUser(userId);
    }

    @Override
    public Page<Policy> listPolicy(ListPolicyRequest request) {
        return baseMapper.selectPage(Pages.toPage(request),
            Wrappers.lambdaQuery(Policy.class)
                .eq(Policy::getApplicationCode, request.getApplicationId())
        );
    }


    @Override
    public void removeUserGroupPolicy(Integer userGroupId, PolicyBindRequest request) {
        request.getPolicyIds().forEach(policyId -> {
        });
    }

    @Override
    public void bindUserPolicy(Integer userId, PolicyBindRequest request) {
        request.getPolicyIds().forEach(policy -> {
            baseMapper.insertUserPolicyRel(userId, policy);
        });
    }

    @Override
    public void bindUserGroupPolicy(Integer groupId, PolicyBindRequest request) {

    }

    @Override
    public void bindRolePolicy(Integer roleId, PolicyBindRequest request) {

    }

}




