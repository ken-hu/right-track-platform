package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.dto.req.AssignApplicationRequest;
import pers.ken.rt.auth.dto.req.BindTenantPolicyRequest;
import pers.ken.rt.auth.dto.req.TenantCreateRequest;
import pers.ken.rt.auth.dto.req.TenantListRequest;
import pers.ken.rt.auth.repository.po.Tenant;

/**
 * The interface Tenant service.
 *
 * @author DELL
 * @description 针对表 【auth_tenant】的数据库操作Service
 * @createDate 2024 -11-20 20:44:40
 */
public interface TenantService extends IService<Tenant> {

    /**
     * Tenant list page.
     *
     * @param request the request
     * @return the page
     */
    Page<Tenant> listTenant(TenantListRequest request);

    /**
     * Tenant create tenant.
     *
     * @param request the request
     * @return the tenant
     */
    Tenant createTenant(TenantCreateRequest request);

    /**
     * Gets by code.
     *
     * @param tenantCode the tenant code
     * @return the by code
     */
    Tenant getByCode(String tenantCode);

    /**
     * Bind tenant policies.
     *
     * @param id      the id
     * @param request the request
     */
    void bindTenantPolicies(Integer id, BindTenantPolicyRequest request);

    /**
     * Bind applications.
     *
     * @param tenantId the tenant id
     * @param request  the request
     */
    void bindApplications(Integer tenantId, AssignApplicationRequest request);
}
