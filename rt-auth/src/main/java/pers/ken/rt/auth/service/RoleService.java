package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.dto.req.PolicyBindRequest;
import pers.ken.rt.auth.dto.req.RoleCreateRequest;
import pers.ken.rt.auth.dto.req.RoleListRequest;
import pers.ken.rt.auth.repository.po.Role;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【role(角色)】的数据库操作Service
 * @createDate 2024-12-24 18:02:42
 */
public interface RoleService extends IService<Role> {

    List<Role> listRolesByUser(Integer userId);

    Page<Role> listRoles(RoleListRequest request);

    Role createRole(RoleCreateRequest request);

    void bindUserRole(Integer userId, Integer roleId);

    void bindRolePolicy(Integer roleId, PolicyBindRequest request);

    void removeRolePolicy(Integer roleId, PolicyBindRequest request);
}
