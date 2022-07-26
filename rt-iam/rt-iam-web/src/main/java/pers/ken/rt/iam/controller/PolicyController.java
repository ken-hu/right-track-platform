package pers.ken.rt.iam.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.iam.dto.req.UserPoliciesReq;
import pers.ken.rt.iam.dto.resp.UserPoliciesResp;
import pers.ken.rt.iam.service.PolicyService;

/**
 * <code> PolicyController </code>
 * <desc> PolicyController </desc>
 * <b>Creation Time:</b> 2022/7/19 17:33.
 *
 * @author Ken.Hu
 */
@RestController
@AllArgsConstructor
public class PolicyController {
    private PolicyService policyService;

    @PostMapping(value = "/users/{userId}/policies", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserPoliciesResp listPolicies(
                                         @PathVariable("userId") String userId,
                                         @RequestBody UserPoliciesReq userPoliciesReq) {
        return policyService.listUserPolicies("", userPoliciesReq);
    }
}
