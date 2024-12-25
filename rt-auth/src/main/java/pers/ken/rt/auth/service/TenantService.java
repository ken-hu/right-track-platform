package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.controller.req.TenantCreateReq;
import pers.ken.rt.auth.controller.req.TenantListReq;
import pers.ken.rt.auth.repository.po.Tenant;

/**
 * @author DELL
 * @description 针对表【auth_tenant】的数据库操作Service
 * @createDate 2024-11-20 20:44:40
 */
public interface TenantService extends IService<Tenant> {

    Page<Tenant> pageQuery(TenantListReq req);

    Tenant createTenant(TenantCreateReq req);

    Tenant getByCode(String tenantCode);
}
