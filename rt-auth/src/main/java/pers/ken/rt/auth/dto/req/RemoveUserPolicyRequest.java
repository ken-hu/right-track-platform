package pers.ken.rt.auth.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: RemoveUserPolicyReq
 * @Created: 2024/12/9 18:11
 * @Author ken
 */
@Data
public class RemoveUserPolicyRequest {
    @NotEmpty
    private List<Integer> policyIds;
}
