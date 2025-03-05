package pers.ken.rt.auth.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: AssignUserPolicyReq
 * @Created: 2024/12/9 17:51
 * @Author ken
 */
@Data
public class AssignPoliciesRequest {
    @NotEmpty
    private List<Integer> policyIds;
}
