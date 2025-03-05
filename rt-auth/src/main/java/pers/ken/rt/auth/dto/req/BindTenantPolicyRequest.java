package pers.ken.rt.auth.dto.req;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: BindTenantPolicyRequest
 * @Created: 2025/3/3 21:09
 * @Author ken
 */
@Data
public class BindTenantPolicyRequest {
    private List<Integer> policyIds;
}
