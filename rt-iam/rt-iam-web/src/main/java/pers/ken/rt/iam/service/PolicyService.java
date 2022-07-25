package pers.ken.rt.iam.service;

import pers.ken.rt.iam.dto.req.UserPoliciesReq;
import pers.ken.rt.iam.dto.resp.UserPoliciesResp;

/**
 * <code> PolicyService </code>
 * <desc> PolicyService </desc>
 * <b>Creation Time:</b> 2022/7/19 17:40.
 *
 * @author Ken.Hu
 */
public interface PolicyService {
    void createPolicy();

    UserPoliciesResp listUserPolicies(String accessToken, UserPoliciesReq userPoliciesReq);
}
