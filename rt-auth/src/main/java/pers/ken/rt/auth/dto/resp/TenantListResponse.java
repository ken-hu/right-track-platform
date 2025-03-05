package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: TenantListResp
 * @Created: 2024/11/4 16:08
 * @Author ken
 */
@Data
public class TenantListResponse {
    private Integer id;
    private String tenantCode;
    private String tenantName;
}
