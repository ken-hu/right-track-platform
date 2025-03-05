package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.dto.req.*;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.mapper.UserGroupMapper;
import pers.ken.rt.auth.repository.po.UserGroup;
import pers.ken.rt.auth.service.UserGroupService;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【user_group(用户组)】的数据库操作Service实现
 * @createDate 2024-12-10 10:39:52
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup>
    implements UserGroupService {

    @Override
    public Page<UserGroup> listByTenant(UserGroupListRequest request) {
        return baseMapper.selectPage(
            Pages.toPage(request),
            Wrappers.lambdaQuery(UserGroup.class)
                .eq(UserGroup::getTenantCode, AccountContext.getTenantCode())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUsersToGroup(Integer groupId, AddUsersToUserGroupRequest request) {
        request.getUserIds().forEach(userId -> {
            List<UserGroup> userGroups = baseMapper.selectUserGroups(userId);
            if (!userGroups.stream().map(UserGroup::getId).toList().contains(groupId)) {
                baseMapper.insertUserGroupRel(userId, groupId);
            }
        });
    }

    @Override
    public List<UserGroup> listUserGroups(Integer userId) {
        return baseMapper.selectUserGroups(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserGroup createUserGroup(UserGroupCreateRequest request) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(request.getName());
        userGroup.setTenantCode(AccountContext.getTenantCode());
        userGroup.setDescription(request.getDescription());
        baseMapper.insert(userGroup);
        return userGroup;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserGroup updateUserGroup(Integer userGroupId, UserGroupUpdateRequest request) {
        UserGroup userGroup = baseMapper.selectById(userGroupId);
        userGroup.setName(request.getName());
        userGroup.setDescription(request.getDescription());
        baseMapper.updateById(userGroup);
        return userGroup;
    }

    @Override
    public void bindUserGroupPolicy(Integer groupId, AssignPoliciesRequest request) {
        request.getPolicyIds().forEach(policyId -> {
            baseMapper.insertUserGroupPolicyRel(groupId, policyId);
        });
    }

}
