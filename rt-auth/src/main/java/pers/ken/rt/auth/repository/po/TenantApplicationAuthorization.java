package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName tenant_application_authorization
 */
@TableName(value = "tenant_application_authorization")
@Data
public class TenantApplicationAuthorization implements Serializable {
    private Integer id;

    private Integer tenantId;

    private Integer appId;

    private String createdBy;

    private LocalDateTime createdAt;

    private static final long serialVersionUID = 1L;
}
