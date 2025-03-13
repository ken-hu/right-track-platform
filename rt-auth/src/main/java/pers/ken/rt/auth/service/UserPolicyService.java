package pers.ken.rt.auth.service;

import pers.ken.rt.auth.dto.req.PolicyBindRequest;
import pers.ken.rt.auth.repository.po.Policy;

import java.util.List;

/**
 * @ClassName: UserPolicyService
 * @Created: 2025/3/7 12:04
 * @Author ken
 */
public interface UserPolicyService {
    void bindUserPolicy(Integer userId, PolicyBindRequest request);

    List<Policy> listMyPolicy();
}
