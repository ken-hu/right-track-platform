package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.dto.req.AssignPoliciesRequest;
import pers.ken.rt.auth.dto.req.PolicyListRequest;
import pers.ken.rt.auth.repository.po.Policy;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【policy】的数据库操作Service
 * @createDate 2024-12-07 15:19:39
 */
public interface PolicyService extends IService<Policy> {

    void removeUserPolicy(Integer userId, AssignPoliciesRequest request);

    List<Policy> listMyPolicies();

    Page<Policy> listPolicies(PolicyListRequest query);

    void removeUserGroupPolicy(Integer userGroupId, AssignPoliciesRequest request);
}
