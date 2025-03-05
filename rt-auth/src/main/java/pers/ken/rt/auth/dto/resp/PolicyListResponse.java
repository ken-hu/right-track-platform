package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: PolicyListResp
 * @Created: 2024/12/9 20:21
 * @Author ken
 */
@Data
public class PolicyListResponse {
    private Integer id;
    private String policyCode;
    private String name;
}
