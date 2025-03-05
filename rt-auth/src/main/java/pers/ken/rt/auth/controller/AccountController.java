package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.assemble.AccountConverter;
import pers.ken.rt.auth.dto.req.AssignPoliciesRequest;
import pers.ken.rt.auth.dto.req.PasswordRestRequest;
import pers.ken.rt.auth.dto.req.UserListRequest;
import pers.ken.rt.auth.dto.resp.UserListResponse;
import pers.ken.rt.auth.oauth.model.AuthUserDetails;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.service.AccountService;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

import java.util.List;
import java.util.Map;

/**
 * The type User controller.
 *
 * @author Ken
 * @className: UserController
 * @createdTime: 2023 /3/7 1:48
 * @desc:
 */
@Tag(name = "user", description = "账户")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "我的信息")
    @GetMapping("/v1/user")
    public AuthUserDetails me() {
        return AccountContext.getUserDetails();
    }

    @Operation(summary = "我的信息")
    @GetMapping("/v1/oauth-user")
    public Map<String, Object> principalGet() {
        return AccountContext.getPrincipalMap();
    }

    @Operation(summary = "重置我的密码")
    @PostMapping("/v1/user/password/reset")
    public void resetPassword(@RequestBody @Validated PasswordRestRequest request) {
        accountService.resetPassword(request);
    }

    @AccessManager
    @Operation(summary = "禁用用户")
    @PutMapping("/v1/users/{userId}/disabled")
    public void userDisabled(@PathVariable Integer userId) {
        accountService.userDisabled(userId);
    }

    @AccessManager
    @Operation(summary = "用户列表")
    @GetMapping("/v1/users")
    public List<UserListResponse> userListAll(UserListRequest request) {
        return AccountConverter.INSTANCE.convert(
            accountService.listByQuery(request)
        );
    }

    @AccessManager
    @Operation(summary = "用户组的用户")
    @PostMapping("/v1/user-groups/{userGroupId}/users")
    public List<UserListResponse> listUserGroupUsers(@PathVariable Integer userGroupId) {
        return AccountConverter.INSTANCE.convert(
            accountService.listByUserGroup(userGroupId)
        );
    }


    @AccessManager
    @Operation(summary = "分配用户策略")
    @PostMapping("/v1/users/{userId}/policies")
    public void assignUserPolicy(@PathVariable Integer userId,
                                 @RequestBody @Validated AssignPoliciesRequest request) {
        accountService.bindUserPolicy(userId, request);
    }
}
