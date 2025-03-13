package pers.ken.rt.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.assemble.TenantConverter;
import pers.ken.rt.auth.dto.req.AssignApplicationRequest;
import pers.ken.rt.auth.dto.req.BindTenantPolicyRequest;
import pers.ken.rt.auth.dto.req.TenantCreateRequest;
import pers.ken.rt.auth.dto.req.TenantListRequest;
import pers.ken.rt.auth.dto.resp.TenantDetailGetResponse;
import pers.ken.rt.auth.dto.resp.TenantListResponse;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.po.Tenant;
import pers.ken.rt.auth.service.TenantService;
import pers.ken.rt.common.model.PageResponse;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

/**
 * @author Ken
 * @className: TenantController
 * @createdTime: 2023/5/24 16:20
 * @desc:
 */
@RestController
@Tag(name = "tenant", description = "租户")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @AccessManager
    @Operation(summary = "租户详细信息")
    @GetMapping("/v1/tenants/{id}")
    public TenantDetailGetResponse detail(@PathVariable Integer id) {
        Tenant tenant = tenantService.getById(id);
        return TenantConverter.INSTANCE.convert(tenant);
    }

    @AccessManager
    @Operation(summary = "租户列表")
    @GetMapping("/v1/tenants")
    public PageResponse<TenantListResponse> tenantList(TenantListRequest request) {
        Page<Tenant> result = tenantService.listTenant(request);
        return Pages.convert(result, TenantConverter.INSTANCE::toListResponse);
    }

    @AccessManager
    @Operation(summary = "创建租户")
    @PostMapping("/v1/tenants")
    public TenantDetailGetResponse tenantCreate(@RequestBody TenantCreateRequest request) {
        Tenant tenant = tenantService.createTenant(request);
        return TenantConverter.INSTANCE.convert(tenant);
    }

    @AccessManager
    @Operation(summary = "给租户授权应用")
    @PostMapping("/v1/tenants/{tenantId}/applications")
    public void bindApplications(@PathVariable Integer tenantId,
                                 @RequestBody AssignApplicationRequest request) {
        tenantService.bindApplications(tenantId, request);
    }

    @Operation(summary = "租户绑定授权策略")
    @PostMapping("/v1/tenants/{id}/policies")
    public void bindTenantPolicies(@PathVariable Integer id, @RequestBody BindTenantPolicyRequest request) {
        tenantService.bindTenantPolicies(id, request);
    }
}
