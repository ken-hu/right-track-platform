package pers.ken.rt.auth.controller.resp;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName: TenantDetail
 * @Created: 2024/11/4 16:07
 * @Author ken
 */
@Data
public class TenantDetailResp {
    private Integer id;

    private String tenantCode;

    private String name;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
