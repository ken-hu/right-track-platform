package pers.ken.rt.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.assemble.UserGroupConverter;
import pers.ken.rt.auth.dto.req.*;
import pers.ken.rt.auth.dto.resp.UserGroupGetResponse;
import pers.ken.rt.auth.dto.resp.UserGroupListResponse;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.po.UserGroup;
import pers.ken.rt.auth.service.UserGroupService;
import pers.ken.rt.common.model.PageResponse;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

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
    @Operation(summary = "创建用户组")
    @PostMapping("/v1/user-groups")
    public UserGroupGetResponse createUserGroup(@RequestBody CreateUserGroupRequest request) {
        UserGroup group = userGroupService.createUserGroup(request);
        return UserGroupConverter.INSTANCE.toGetResponse(group);
    }

    @AccessManager
    @Operation(summary = "更新用户组")
    @PutMapping("/v1/user-groups/{groupId}")
    public UserGroupGetResponse createUserGroup(@PathVariable Integer groupId,
                                                @RequestBody UserGroupUpdateRequest request) {
        UserGroup group = userGroupService.updateUserGroup(groupId, request);
        return UserGroupConverter.INSTANCE.toGetResponse(group);
    }

    @AccessManager
    @Operation(summary = "用户添加到用户组")
    @PostMapping("/v1/user-groups/{groupId}/users")
    public void addUsersToGroup(@PathVariable Integer groupId, @RequestBody @Validated AddUsersToUserGroupRequest request) {
        userGroupService.addUsersToGroup(groupId, request);
    }

    @AccessManager
    @Operation(summary = "查询用户组列表")
    @GetMapping("/v1/user-groups")
    public PageResponse<UserGroupListResponse> groups(ListUserGroupRequest request) {
        Page<UserGroup> pages = userGroupService.listUserGroups(request);
        return Pages.convert(pages, UserGroupConverter.INSTANCE::toList);
    }

    @AccessManager
    @Operation(summary = "移除用户组策略")
    @DeleteMapping("/v1/user-groups/{groupId}/policies")
    public void removeUserGroupPolicy(@PathVariable Integer groupId,
                                      @RequestBody @Validated PolicyBindRequest request) {
    }

    @AccessManager
    @Operation(summary = "给用户组分配策略")
    @PostMapping("/v1/user-groups/{groupId}/policies")
    public void assignUserGroupPolicies(@PathVariable Integer groupId,
                                        @Validated PolicyBindRequest request) {
    }
}
