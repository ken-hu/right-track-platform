package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName auth_tenant
 */
@TableName(value = "tenant")
@Data
public class Tenant implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String tenantCode;

    private String tenantName;

    private String status;

    private Integer ownerUserId;

    private String contactEmail;

    private String contactPhone;

    private String ext;
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}
