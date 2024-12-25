package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.controller.req.TenantCreateReq;
import pers.ken.rt.auth.controller.req.TenantListReq;
import pers.ken.rt.auth.repository.mapper.TenantMapper;
import pers.ken.rt.auth.repository.po.Tenant;
import pers.ken.rt.auth.service.TenantService;

/**
 * @author DELL
 * @description 针对表【auth_tenant】的数据库操作Service实现
 * @createDate 2024-11-20 20:44:40
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
        implements TenantService {

    @Override
    public Page<Tenant> pageQuery(TenantListReq req) {
        return page(new Page<>(req.getPage(), req.getPerPage()));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tenant createTenant(TenantCreateReq req) {
        Tenant tenant = new Tenant();
        tenant.setName(req.getName());
        tenant.setStatus("enabled");
        this.save(tenant);
        return tenant;
    }

    @Override
    public Tenant getByCode(String tenantCode) {
        return this.getOne(
                Wrappers.lambdaQuery(Tenant.class)
                        .eq(Tenant::getTenantCode, tenantCode)
        );
    }
}




