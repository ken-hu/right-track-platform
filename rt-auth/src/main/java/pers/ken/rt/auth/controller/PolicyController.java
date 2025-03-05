package pers.ken.rt.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.assemble.PolicyConverter;
import pers.ken.rt.auth.dto.req.AssignPoliciesRequest;
import pers.ken.rt.auth.dto.req.PolicyListRequest;
import pers.ken.rt.auth.dto.resp.PolicyDetailResponse;
import pers.ken.rt.auth.dto.resp.PolicyListResponse;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.PolicyService;
import pers.ken.rt.common.model.PageResponse;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PolicyController
 * @Created: 2024/12/6 17:48
 * @Author ken
 */
@Tag(name = "policy", description = "策略")
@RestController
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @Operation(summary = "我的策略")
    @GetMapping("/v1/user/policies")
    public List<PolicyDetailResponse> myPolicies() {
        List<Policy> policies = policyService.listMyPolicies();
        return PolicyConverter.INSTANCE.convert(policies);
    }

    @Operation(summary = "租户下的策略")
    @GetMapping("/v1/tenant/policies")
    public List<PolicyListResponse> myTenantPolicies() {
        return new ArrayList<>();
    }


    @AccessManager
    @Operation(summary = "策略列表")
    @GetMapping("/v1/policies")
    public PageResponse<PolicyListResponse> listPolicies(@Validated PolicyListRequest request) {
        Page<Policy> policies = policyService.listPolicies(request);
        return Pages.convert(policies, PolicyConverter.INSTANCE::toList);
    }

    @AccessManager
    @Operation(summary = "策略详细信息")
    @GetMapping("/v1/policies/{id}")
    public PolicyDetailResponse get(@PathVariable Integer id) {
        Policy policy = policyService.getById(id);
        return PolicyConverter.INSTANCE.convert(policy);
    }


    @AccessManager
    @Operation(summary = "移除用户策略")
    @DeleteMapping("/v1/users/{userId}/policies")
    public void removeUserPolicy(@PathVariable Integer userId,
                                 @RequestBody @Validated AssignPoliciesRequest request) {
        policyService.removeUserPolicy(userId, request);
    }

    @AccessManager
    @Operation(summary = "移除用户组策略")
    @DeleteMapping("/v1/users/{userGroupId}/policies")
    public void removeUserGroupPolicy(@PathVariable Integer userGroupId,
                                      @RequestBody @Validated AssignPoliciesRequest request) {
        policyService.removeUserGroupPolicy(userGroupId, request);
    }

    @AccessManager
    @Operation(summary = "移除角色策略")
    @DeleteMapping("/v1/users/{roleId}/policies")
    public void removeRolePolicy(@PathVariable Integer roleId,
                                 @RequestBody @Validated AssignPoliciesRequest request) {
    }

}
