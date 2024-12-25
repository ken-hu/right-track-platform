package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.controller.req.AddUsersToUserGroupReq;
import pers.ken.rt.auth.repository.po.UserGroup;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【user_group(用户组)】的数据库操作Service
 * @createDate 2024-12-10 10:39:52
 */
public interface UserGroupService extends IService<UserGroup> {

    List<UserGroup> listByTenant();

    void addUsersToGroup(Integer groupId, AddUsersToUserGroupReq req);

    List<UserGroup> listUserGroups(Integer userId);
}
