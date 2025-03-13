package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName department
 */
@TableName(value = "department")
@Data
public class Department implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String tenantCode;
    private String code;

    private String parentCode;

    private String name;

    private String description;
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}
