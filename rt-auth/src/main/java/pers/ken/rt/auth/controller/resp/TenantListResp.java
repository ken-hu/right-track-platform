package pers.ken.rt.auth.controller.resp;

import lombok.Data;

/**
 * @ClassName: TenantListResp
 * @Created: 2024/11/4 16:08
 * @Author ken
 */
@Data
public class TenantListResp {
    private Integer tenantId;
    private String name;
}