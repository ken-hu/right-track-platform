package pers.ken.rt.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @ClassName: TenantCreateReq
 * @Created: 2024/12/7 16:10
 * @Author ken
 */
@Data
public class TenantCreateRequest {
    private String tenantCode;
    @NotBlank
    private String tenantName;
    @NotBlank
    private String contactName;
    @NotBlank
    private String contactEmail;
    @NotBlank
    private String contactPhone;
}
