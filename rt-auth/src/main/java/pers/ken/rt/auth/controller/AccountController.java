package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.assemble.AccountConverter;
import pers.ken.rt.auth.controller.assemble.PolicyConverter;
import pers.ken.rt.auth.dto.req.PasswordRestRequest;
import pers.ken.rt.auth.dto.req.PolicyBindRequest;
import pers.ken.rt.auth.dto.req.UserListRequest;
import pers.ken.rt.auth.dto.resp.GetUserDetailResponse;
import pers.ken.rt.auth.dto.resp.ListUserResponse;
import pers.ken.rt.auth.dto.resp.PolicyDetailResponse;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.po.Account;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.AccountService;
import pers.ken.rt.auth.service.UserPolicyService;
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
    private final UserPolicyService userPolicyService;

    @Operation(summary = "我的信息")
    @GetMapping("/v1/users/me")
    public GetUserDetailResponse me() {
        Account account = accountService.getById(AccountContext.getUserId());
        return AccountConverter.INSTANCE.toDetail(account);
    }

    @Operation(summary = "我的信息")
    @GetMapping("/v1/users/me/principal")
    public Map<String, Object> principalGet() {
        return AccountContext.getPrincipalMap();
    }

    @Operation(summary = "重置我的密码")
    @PostMapping("/v1/users/me/password/reset")
    public void resetPassword(@RequestBody @Validated PasswordRestRequest request) {
        accountService.resetPassword(request);
    }

    @AccessManager
    @Operation(summary = "禁用用户")
    @PutMapping("/v1/users/{userId}/disable")
    public void userDisabled(@PathVariable Integer userId) {
        accountService.userDisable(userId);
    }

    @AccessManager
    @Operation(summary = "用户列表")
    @GetMapping("/v1/users")
    public List<ListUserResponse> userListAll(UserListRequest request) {
        return AccountConverter.INSTANCE.convert(
            accountService.listByQuery(request)
        );
    }

    @AccessManager
    @Operation(summary = "给用户分配策略")
    @PostMapping("/v1/users/{userId}/policies")
    public void bindUserPolicy(@PathVariable Integer userId,
                               @RequestBody @Validated PolicyBindRequest request) {
        userPolicyService.bindUserPolicy(userId, request);
    }

    @Operation(summary = "我的策略")
    @GetMapping("/v1/users/me/policies")
    public List<PolicyDetailResponse> listMyPolicy() {
        List<Policy> policies = userPolicyService.listMyPolicy();
        return PolicyConverter.INSTANCE.convert(policies);
    }

    @AccessManager
    @Operation(summary = "移除用户策略")
    @DeleteMapping("/v1/users/{userId}/policies")
    public void removeUserPolicy(@PathVariable Integer userId,
                                 @RequestBody @Validated PolicyBindRequest request) {
    }
}
