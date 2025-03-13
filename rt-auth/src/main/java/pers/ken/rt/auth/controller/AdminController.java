package pers.ken.rt.auth.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.controller.assemble.PolicyConverter;
import pers.ken.rt.auth.dto.resp.PolicyListResponse;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.auth.service.PolicyService;
import pers.ken.rt.common.model.PageResponse;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

import java.util.List;

/**
 * @ClassName: AdminController
 * @Created: 2025/3/10 10:18
 * @Author ken
 */
@RestController
@Tag(name = "admin", description = "超级管理员")
@RequiredArgsConstructor
public class AdminController {
    private final PolicyService policyService;

    @AccessManager
    @Operation(summary = "所有的策略列表")
    @DeleteMapping("/v1/admin/policies")
    public PageResponse<PolicyListResponse> allPolicy() {
        List<Policy> policies = policyService.list(
            Wrappers.lambdaQuery(Policy.class)
                .select(Policy::getId, Policy::getPolicyCode, Policy::getName)
        );
        return Pages.convert(policies, PolicyConverter.INSTANCE::toList);
    }
}
