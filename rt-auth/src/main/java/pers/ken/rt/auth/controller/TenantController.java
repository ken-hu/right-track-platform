package pers.ken.rt.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.controller.convert.TenantConverter;
import pers.ken.rt.auth.controller.req.TenantCreateReq;
import pers.ken.rt.auth.controller.req.TenantListReq;
import pers.ken.rt.auth.controller.resp.TenantDetailResp;
import pers.ken.rt.auth.controller.resp.TenantListResp;
import pers.ken.rt.auth.oauth.utils.AccountContext;
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
@Tag(name = "tenant")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @Operation(summary = "我的租户")
    @GetMapping("/v1/user/tenants")
    public TenantDetailResp myTenant() {
        Tenant tenant = tenantService.getByCode(AccountContext.getTenantCode());
        return TenantConverter.INSTANCE.convert(tenant);
    }

    @AccessManager
    @Operation(summary = "租户信息")
    @GetMapping("/v1/tenants/{tenantId}")
    public TenantDetailResp detail(@PathVariable Integer tenantId) {
        Tenant tenant = tenantService.getById(tenantId);
        return TenantConverter.INSTANCE.convert(tenant);
    }

    @AccessManager
    @Operation(summary = "租户列表")
    @GetMapping("/v1/tenants")
    public PageResponse<TenantListResp> create(TenantListReq req) {
        Page<Tenant> result = tenantService.pageQuery(req);
        return Pages.convert(result, TenantConverter.INSTANCE::toList);
    }

    @AccessManager
    @Operation(summary = "创建租户")
    @PostMapping("/v1/tenants")
    public TenantDetailResp create(TenantCreateReq req) {
        Tenant tenant = tenantService.createTenant(req);
        return TenantConverter.INSTANCE.convert(tenant);
    }
}
