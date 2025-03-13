package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.dto.req.ListPolicyRequest;
import pers.ken.rt.auth.dto.req.PolicyBindRequest;
import pers.ken.rt.auth.repository.po.Policy;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【policy】的数据库操作Service
 * @createDate 2024-12-07 15:19:39
 */
public interface PolicyService extends IService<Policy> {


    List<Policy> listPolicyByUser(Integer userId);

    Page<Policy> listPolicy(ListPolicyRequest query);

    void removeUserPolicy(Integer userId, PolicyBindRequest request);

    void removeUserGroupPolicy(Integer userGroupId, PolicyBindRequest request);

    void bindUserPolicy(Integer userId, PolicyBindRequest request);

    void bindUserGroupPolicy(Integer groupId, PolicyBindRequest request);

    void bindRolePolicy(Integer roleId, PolicyBindRequest request);
}
