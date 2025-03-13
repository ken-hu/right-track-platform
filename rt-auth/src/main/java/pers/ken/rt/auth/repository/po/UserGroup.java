package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName user_group
 */
@TableName(value = "user_group")
@Data
public class UserGroup implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String tenantCode;

    private String name;

    private String description;
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}
