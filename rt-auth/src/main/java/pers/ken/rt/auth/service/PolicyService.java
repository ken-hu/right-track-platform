package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.controller.req.AssignPoliciesReq;
import pers.ken.rt.auth.repository.po.Policy;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【policy】的数据库操作Service
 * @createDate 2024-12-07 15:19:39
 */
public interface PolicyService extends IService<Policy> {
    void assignUserPolicy(Integer userId, AssignPoliciesReq req);

    void removeUserPolicy(Integer userId, AssignPoliciesReq req);

    List<Policy> listMyPolicies();

    List<Policy> listPolicies();

    void assignUserGroupPolicy(Integer groupId, AssignPoliciesReq req);

    Policy loadByCode(String policyCode);
}
