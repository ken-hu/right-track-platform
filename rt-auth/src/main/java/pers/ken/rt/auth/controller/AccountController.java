package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.convert.AccountConverter;
import pers.ken.rt.auth.controller.req.PasswordRestReq;
import pers.ken.rt.auth.controller.req.UserListReq;
import pers.ken.rt.auth.controller.resp.UserListResp;
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
    public void resetPassword(@RequestBody @Validated PasswordRestReq req) {
        accountService.resetPassword(req);
    }

    @AccessManager
    @Operation(summary = "禁用用户")
    @PutMapping("/v1/users/{userId}/disabled")
    public void userDisabled(@PathVariable Integer userId) {
        accountService.disabledUser(userId);
    }

    @AccessManager
    @Operation(summary = "用户列表")
    @GetMapping("/v1/users")
    public List<UserListResp> listAllUsers(UserListReq req) {
        return AccountConverter.INSTANCE.convert(
                accountService.listByQuery(req)
        );
    }
}
