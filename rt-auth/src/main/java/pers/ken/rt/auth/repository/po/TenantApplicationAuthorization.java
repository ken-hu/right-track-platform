package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    private static final long serialVersionUID = 1L;
}
