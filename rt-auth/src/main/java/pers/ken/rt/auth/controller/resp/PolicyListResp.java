package pers.ken.rt.auth.controller.resp;

import lombok.Data;

/**
 * @ClassName: PolicyListResp
 * @Created: 2024/12/9 20:21
 * @Author ken
 */
@Data
public class PolicyListResp {
    private Integer id;
    private String policyCode;
    private String name;
}
