package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.convert.UserGroupConverter;
import pers.ken.rt.auth.controller.req.AddUsersToUserGroupReq;
import pers.ken.rt.auth.controller.resp.UserGroupListResp;
import pers.ken.rt.auth.repository.po.UserGroup;
import pers.ken.rt.auth.service.UserGroupService;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

import java.util.List;

/**
 * @ClassName: UserGroupController
 * @Created: 2024/12/7 17:27
 * @Author ken
 */
@RestController
@Tag(name = "user-group", description = "用户组")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupService userGroupService;

    @AccessManager
    @Operation(summary = "查询用户的用户组")
    @GetMapping("/v1/users/{userId}/user-groups")
    public List<UserGroupListResp> userGroups(@PathVariable Integer userId) {
        List<UserGroup> groups = userGroupService.listUserGroups(userId);
        return UserGroupConverter.INSTANCE.toList(groups);
    }

    @AccessManager
    @Operation(summary = "用户添加到用户组")
    @PostMapping("/v1/user-groups/{groupId}/users")
    public void addUsersToGroup(@PathVariable Integer groupId, @RequestBody @Validated AddUsersToUserGroupReq req) {
        userGroupService.addUsersToGroup(groupId, req);
    }

    @AccessManager
    @Operation(summary = "查询用户组列表")
    @GetMapping("/v1/user-groups")
    public List<UserGroupListResp> groups() {
        List<UserGroup> groups = userGroupService.listByTenant();
        return UserGroupConverter.INSTANCE.toList(groups);
    }
}
