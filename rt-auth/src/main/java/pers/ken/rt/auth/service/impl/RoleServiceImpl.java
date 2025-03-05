package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.controller.assemble.RoleConverter;
import pers.ken.rt.auth.dto.req.AssignPoliciesRequest;
import pers.ken.rt.auth.dto.req.RoleCreateRequest;
import pers.ken.rt.auth.dto.req.RoleListRequest;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.mapper.RoleMapper;
import pers.ken.rt.auth.repository.po.Role;
import pers.ken.rt.auth.service.RoleService;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【role(角色)】的数据库操作Service实现
 * @createDate 2024-12-24 18:02:42
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Override
    public List<Role> listRolesByUser(Integer userId) {
        return baseMapper.selectByUser(userId);
    }

    @Override
    public Page<Role> listRoles(RoleListRequest request) {
        return baseMapper.selectPage(Pages.toPage(request), Wrappers.emptyWrapper());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createRole(RoleCreateRequest request) {
        Role role = RoleConverter.INSTANCE.toRole(request);
        role.setTenantCode(AccountContext.getTenantCode());
        baseMapper.insert(role);
        return role;
    }

    @Override
    public void bindUserRole(Integer userId, Integer roleId) {

    }

    @Override
    public void bindRolePolicy(Integer roleId, AssignPoliciesRequest request) {
        //TODO 租户的限制校验分配的策略里面是否有超出范围的Policies
        request.getPolicyIds().forEach(policyId -> {
            baseMapper.insertRolePolicyRel(roleId, policyId);
        });
    }
}




