package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.controller.req.AddUsersToUserGroupReq;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.po.UserGroup;
import pers.ken.rt.auth.service.UserGroupService;
import pers.ken.rt.auth.repository.mapper.UserGroupMapper;
import org.springframework.stereotype.Service;

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
    public List<UserGroup> listByTenant() {
        return this.list(
                Wrappers.lambdaQuery(UserGroup.class)
                        .eq(UserGroup::getTenantCode, AccountContext.getTenantCode())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUsersToGroup(Integer groupId, AddUsersToUserGroupReq req) {
        req.getUserIds().forEach(userId -> {
            baseMapper.insertUserGroupRel(userId, groupId);
        });
    }

    @Override
    public List<UserGroup> listUserGroups(Integer userId) {
        return baseMapper.selectUserGroups(userId);
    }
}




