package pers.ken.rt.auth.interfaces.facade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.application.service.PolicyAppService;
import pers.ken.rt.auth.infrastructure.support.Oauth2ContextHolder;
import pers.ken.rt.auth.interfaces.dto.req.PolicySaveRequest;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyItemResp;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyResp;

import java.util.List;

/**
 * Name: PolicyController
 * Creation Time: 2023/1/7 23:25.
 *
 * @author Ken
 */
@RestController
@AllArgsConstructor
@Tag(name = "policies")
public class PolicyController {
    private PolicyAppService policyAppService;

    @Operation(summary = "ListUserPolicies")
    @GetMapping("/v1/users/{id}/policies")
    public List<PolicyItemResp> list(@PathVariable("id") Long id) {
        return policyAppService.userPolicyItems(id);
    }

    @Operation(summary = "CreatePolicy")
    @PostMapping("/v1/policies")
    public void create(@Validated PolicySaveRequest req) {
        policyAppService.create(req);
    }

    @Operation(summary = "UpdatePolicy")
    @PutMapping("/v1/policies/{id}")
    public void update(@PathVariable Long id, @Validated PolicySaveRequest req) {
        policyAppService.update(id, req);
    }

    @Operation(summary = "DeletePolicy")
    @DeleteMapping("/v1/policies/{id}")
    public void delete(@PathVariable Long id) {
        policyAppService.delete(id);
    }

    @Operation(summary = "ListCurrentUserPolicies")
    @GetMapping("/v1/policies")
    public List<PolicyResp> userPolicies() {
        Long userId = Oauth2ContextHolder.getUserId();
        return policyAppService.userPolicies(userId);
    }
}
