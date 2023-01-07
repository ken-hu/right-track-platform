package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.dto.req.PolicyCreateReq;
import pers.ken.rt.auth.service.PolicyService;

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
    public void userPolicies(@PathVariable("id") String id) {

    }

    @PostMapping("/policy")
    public void createPolicy(@RequestBody PolicyCreateReq policyCreateReq) {

    }
}
