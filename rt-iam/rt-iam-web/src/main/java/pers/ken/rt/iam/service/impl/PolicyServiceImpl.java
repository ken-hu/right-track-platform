package pers.ken.rt.iam.service.impl;

import lombok.AllArgsConstructor;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Service;
import pers.ken.rt.iam.dto.req.UserPoliciesReq;
import pers.ken.rt.iam.dto.resp.UserPoliciesResp;
import pers.ken.rt.iam.exception.AccessDenyException;
import pers.ken.rt.iam.exception.AccessErrorCode;
import pers.ken.rt.iam.service.PolicyService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <code> PolicyServiceImpl </code>
 * <desc> PolicyServiceImpl </desc>
 * <b>Creation Time:</b> 2022/7/19 17:40.
 *
 * @author Ken.Hu
 */
@Service
@AllArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private Enforcer enforcer;

    @Override
    public void createPolicy() {
    }

    @Override
    public UserPoliciesResp listPolicies(String userId, UserPoliciesReq userPoliciesReq) {
        // uc->access_token->userInfo
        boolean enforce = enforcer.enforce(userId, userPoliciesReq.getResource(), userPoliciesReq.getAction());
        if (enforce) {
            List<List<String>> policies =
                    enforcer.getFilteredPolicy(0, userId, userPoliciesReq.getResource(), userPoliciesReq.getAction());
            List<UserPoliciesResp.Policy> userPolicies = policies.stream().map(p -> {
                String subject = p.get(0);
                String resource = p.get(1);
                String action = p.get(2);
                return UserPoliciesResp.Policy.builder()
                        .subject(subject)
                        .resource(resource)
                        .action(action)
                        .build();
            }).collect(Collectors.toList());
            return new UserPoliciesResp(userPolicies);
        }
        throw new AccessDenyException(AccessErrorCode.ACCESS_DENY);
    }
}
