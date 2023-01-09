package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.convert.PolicyConvert;
import pers.ken.rt.auth.dto.resp.PolicyResp;
import pers.ken.rt.auth.entity.Policy;
import pers.ken.rt.auth.service.PolicyService;

import java.util.ArrayList;
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

    @GetMapping("/users/{id}/policies")
    public List<PolicyResp> userPolicies(@PathVariable("id") String id) {
        List<Policy> policies = policyService.userPolicies(id);
        if (CollectionUtils.isEmpty(policies)) {
            return new ArrayList<>();
        }
        return PolicyConvert.INSTANCE.convert(policies);
    }
}
