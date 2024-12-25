package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.convert.PolicyConverter;
import pers.ken.rt.auth.controller.req.AssignPoliciesReq;
import pers.ken.rt.auth.controller.resp.PolicyDetailResp;
import pers.ken.rt.auth.controller.resp.PolicyListResp;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.PolicyService;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

import java.util.List;

/**
 * @ClassName: PolicyController
 * @Created: 2024/12/6 17:48
 * @Author ken
 */
@RestController
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @Operation(summary = "我的策略")
    @GetMapping("/v1/user/policies")
    public List<PolicyDetailResp> myPolicies() {
        List<Policy> policies = policyService.listMyPolicies();
        return PolicyConverter.INSTANCE.convert(policies);
    }

    @Operation(summary = "分配用户策略")
    @PostMapping("/v1/users/{userId}/policies")
    @AccessManager
    public void assignUserPolicy(@PathVariable Integer userId,
                                 @RequestBody @Validated AssignPoliciesReq req) {
        policyService.assignUserPolicy(userId, req);
    }

    @Operation(summary = "移除用户策略")
    @DeleteMapping("/v1/users/{userId}/policies")
    @AccessManager
    public void removeUserPolicy(@PathVariable Integer userId,
                                 @RequestBody @Validated AssignPoliciesReq req) {
        policyService.removeUserPolicy(userId, req);
    }

    @Operation(summary = "策略列表")
    @GetMapping("/v1/policies")
    @AccessManager
    public List<PolicyListResp> listPolicies() {
        List<Policy> policies = policyService.listPolicies();
        return PolicyConverter.INSTANCE.toList(policies);
    }

    @Operation(summary = "策略详细信息")
    @GetMapping("/v1/policies/{id}")
    @AccessManager
    public PolicyDetailResp get(@PathVariable Integer id) {
        Policy policy = policyService.getById(id);
        return PolicyConverter.INSTANCE.convert(policy);
    }

    @Operation(summary = "分配用户组策略")
    @PostMapping("/v1/user-groups/{groupId}/policies")
    @AccessManager
    public void assignUserGroupPolicies(@PathVariable Integer groupId, @Validated AssignPoliciesReq req) {
        policyService.assignUserGroupPolicy(groupId, req);
    }
}
