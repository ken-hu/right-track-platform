package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.dto.req.*;
import pers.ken.rt.auth.repository.po.UserGroup;

import java.util.List;

/**
 * The interface User group service.
 *
 * @author DELL
 * @description 针对表 【user_group(用户组)】的数据库操作Service
 * @createDate 2024 -12-10 10:39:52
 */
public interface UserGroupService extends IService<UserGroup> {

    /**
     * List by tenant page.
     *
     * @param request the request
     * @return the page
     */
    Page<UserGroup> listByTenant(UserGroupListRequest request);

    /**
     * Add users to group.
     *
     * @param groupId the group id
     * @param request the request
     */
    void addUsersToGroup(Integer groupId, AddUsersToUserGroupRequest request);

    /**
     * List user groups list.
     *
     * @param userId the user id
     * @return the list
     */
    List<UserGroup> listUserGroups(Integer userId);

    /**
     * Create user group user group.
     *
     * @param request the request
     * @return the user group
     */
    UserGroup createUserGroup(UserGroupCreateRequest request);

    /**
     * Update user group user group.
     *
     * @param userGroupId the user group id
     * @param request     the request
     * @return the user group
     */
    UserGroup updateUserGroup(Integer userGroupId, UserGroupUpdateRequest request);

    void bindUserGroupPolicy(Integer groupId, AssignPoliciesRequest request);
}
