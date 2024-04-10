package pers.ken.rt.starter.pbac.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: AccessDeniedInfo
 * @Created: 2024-04-01 14:24:18
 * @Description:
 * @Author ken
 */
@Data
@AllArgsConstructor
public class AccessDeniedInfo {
    private String policyId;
    private String policyName;
    private String action;
    private String resources;
}
