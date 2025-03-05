package pers.ken.rt.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.assemble.RoleConverter;
import pers.ken.rt.auth.dto.req.AssignPoliciesRequest;
import pers.ken.rt.auth.dto.req.RoleCreateRequest;
import pers.ken.rt.auth.dto.req.RoleListRequest;
import pers.ken.rt.auth.dto.resp.RoleGetResponse;
import pers.ken.rt.auth.dto.resp.RoleListResponse;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.po.Role;
import pers.ken.rt.auth.service.RoleService;
import pers.ken.rt.common.model.PageResponse;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

import java.util.List;

/**
 * @ClassName: RoleController
 * @Created: 2024/12/26 17:48
 * @Author ken
 */
@Tag(name = "role", description = "角色")
@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "我的角色")
    @GetMapping("/v1/user/roles")
    public List<RoleListResponse> myRoles() {
        List<Role> roles = roleService.listRolesByUser(AccountContext.getUserId());
        return RoleConverter.INSTANCE.convert(roles);
    }

    @AccessManager
    @Operation(summary = "角色列表")
    @GetMapping("/v1/roles")
    public PageResponse<RoleListResponse> roles(RoleListRequest request) {
        Page<Role> rolePage = roleService.listRoles(request);
        return Pages.convert(rolePage, RoleConverter.INSTANCE::convert);
    }

    @AccessManager
    @Operation(summary = "创建角色")
    @PostMapping("/v1/roles")
    public RoleGetResponse createRole(@RequestBody RoleCreateRequest request) {
        Role role = roleService.createRole(request);
        return RoleConverter.INSTANCE.toGetResponse(role);
    }

    @AccessManager
    @Operation(summary = "给角色分配策略")
    @PostMapping("/v1/roles/{roleId}/policies")
    public void bindRolePolicy(@PathVariable Integer roleId, @Validated AssignPoliciesRequest request) {
        roleService.bindRolePolicy(roleId, request);
    }
}
