package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: TenantDetail
 * @Created: 2024/11/4 16:07
 * @Author ken
 */
@Data
public class TenantDetailGetResponse {
    private Integer id;
    private String tenantCode;
    private String tenantName;
    private String status;
}
