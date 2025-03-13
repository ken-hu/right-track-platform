package pers.ken.rt.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.controller.assemble.PolicyConverter;
import pers.ken.rt.auth.dto.req.ListPolicyRequest;
import pers.ken.rt.auth.dto.resp.PolicyDetailResponse;
import pers.ken.rt.auth.dto.resp.PolicyListResponse;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.PolicyService;
import pers.ken.rt.common.model.PageResponse;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

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

    @AccessManager
    @Operation(summary = "策略列表")
    @GetMapping("/v1/policies")
    public PageResponse<PolicyListResponse> listPolicy(@Validated ListPolicyRequest request) {
        Page<Policy> policies = policyService.listPolicy(request);
        return Pages.convert(policies, PolicyConverter.INSTANCE::toList);
    }

    @AccessManager
    @Operation(summary = "策略详细信息")
    @GetMapping("/v1/policies/{id}")
    public PolicyDetailResponse get(@PathVariable Integer id) {
        Policy policy = policyService.getById(id);
        return PolicyConverter.INSTANCE.convert(policy);
    }

}
