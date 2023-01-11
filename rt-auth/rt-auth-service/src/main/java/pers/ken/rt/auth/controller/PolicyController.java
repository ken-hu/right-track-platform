package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.convert.PolicyConvert;
import pers.ken.rt.auth.dto.req.PolicyCreateReq;
import pers.ken.rt.auth.dto.resp.PolicyResp;
import pers.ken.rt.auth.entity.OauthUser;
import pers.ken.rt.auth.entity.Policy;
import pers.ken.rt.auth.service.OauthUserService;
import pers.ken.rt.auth.service.PolicyService;

import java.util.List;

/**
 * Name: PolicyController
 * Creation Time: 2023/1/7 23:25.
 *
 * @author Ken
 */
@RestController
@AllArgsConstructor
@Tag(name = "Policy")
public class PolicyController {
    private PolicyService policyService;
    private OauthUserService oauthUserService;

    @GetMapping("/users/{id}/policies")
    @Operation(summary = "ListUserPolicies")
    public List<PolicyResp> userPolicies(@PathVariable("id") Long id) {
        OauthUser oauthUser = oauthUserService.getOauthUser(id);
        List<Policy> policies = oauthUser.getPolicies();
        return PolicyConvert.INSTANCE.convert(policies);
    }

    @GetMapping("/users/policies")
    @Operation(summary = "ListCurrentUserPolicies")
    public List<PolicyResp> userPolicies() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OauthUser oauthUser = oauthUserService.getOauthUser(1L);
        List<Policy> policies = oauthUser.getPolicies();
        return PolicyConvert.INSTANCE.convert(policies);
    }

    @PostMapping("/policies")
    @Operation(summary = "CreatePolicy")
    public void createPolicy(@Validated PolicyCreateReq req) {
        policyService.createPolicy(req);
    }
}
