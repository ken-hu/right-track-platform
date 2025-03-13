package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.cons.SystemPreRoleConstant;
import pers.ken.rt.auth.dto.req.AssignApplicationRequest;
import pers.ken.rt.auth.dto.req.BindTenantPolicyRequest;
import pers.ken.rt.auth.dto.req.TenantCreateRequest;
import pers.ken.rt.auth.dto.req.TenantListRequest;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.mapper.TenantMapper;
import pers.ken.rt.auth.repository.po.Account;
import pers.ken.rt.auth.repository.po.Role;
import pers.ken.rt.auth.repository.po.Tenant;
import pers.ken.rt.auth.repository.po.TenantApplicationAuthorization;
import pers.ken.rt.auth.service.AccountService;
import pers.ken.rt.auth.service.RoleService;
import pers.ken.rt.auth.service.TenantApplicationAuthorizationService;
import pers.ken.rt.auth.service.TenantService;
import pers.ken.rt.common.exception.BusinessVerificationException;
import pers.ken.rt.common.exception.ErrorCode;

import java.util.List;
import java.util.UUID;

/**
 * @author DELL
 * @description 针对表【auth_tenant】的数据库操作Service实现
 * @createDate 2024-11-20 20:44:40
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
    implements TenantService {

    private final RoleService roleService;
    private final AccountService accountService;
    private final TenantApplicationAuthorizationService tenantApplicationAuthorizationService;


    @Override
    public Page<Tenant> listTenant(TenantListRequest request) {
        return baseMapper.selectPage(
            Pages.toPage(request),
            Wrappers.emptyWrapper());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tenant createTenant(TenantCreateRequest request) {
        Tenant tenant = baseMapper.selectById(AccountContext.getTenantId());
        if (null != tenant) {
            throw new BusinessVerificationException(ErrorCode.INVALID_ARGUMENTS, String.format("TenantCode '%s' repeat.", request.getTenantCode()));
        }
        String tenantCode = request.getTenantCode();
        if (StringUtils.isBlank(request.getTenantCode())) {
            tenantCode = UUID.randomUUID().toString().replaceAll("-", "");
        }

        // 注册企业联系人账号
        Account account = new Account();
        account.setNickname(account.getNickname());
        account.setEmail(account.getEmail());
        account.setTenantCode(tenantCode);
        accountService.save(account);
        Integer ownerUserId = account.getId();

        // 租户创建
        Tenant newTenant = new Tenant();
        newTenant.setTenantName(request.getTenantName());
        newTenant.setTenantCode(tenantCode);
        newTenant.setStatus("enabled");
        newTenant.setOwnerUserId(ownerUserId);
        newTenant.setContactEmail(request.getContactEmail());
        newTenant.setContactPhone(request.getContactPhone());
        baseMapper.insert(newTenant);

        // 创建预设的默认企业管理员角色
        Role role = new Role();
        role.setTenantCode(tenantCode);
        role.setName(SystemPreRoleConstant.TENANT_ADMIN);
        role.setDescription(request.getTenantName() + "_租户管理员");
        roleService.save(role);

        // 把角色分配给用户
        roleService.bindUserRole(ownerUserId, role.getId());
        return newTenant;
    }

    @Override
    public Tenant getByCode(String tenantCode) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(Tenant.class)
                .eq(Tenant::getTenantCode, tenantCode)
        );
    }

    @Override
    public void bindTenantPolicies(Integer id, BindTenantPolicyRequest request) {
        // 找到租户对应的管理员角色
        // 授权给管理员角色
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindApplications(Integer tenantId, AssignApplicationRequest request) {
        List<TenantApplicationAuthorization> tenantApplicationAuthorizations = request.getApplicationIds()
            .stream().map(appId -> {
                    TenantApplicationAuthorization authorization = new TenantApplicationAuthorization();
                    authorization.setAppId(appId);
                    authorization.setTenantId(tenantId);
                    return authorization;
                }
            ).toList();

        tenantApplicationAuthorizationService.saveBatch(tenantApplicationAuthorizations);
    }
}




