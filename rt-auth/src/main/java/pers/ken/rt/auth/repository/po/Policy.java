package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName policy
 */
@TableName(value = "policy", autoResultMap = true)
@Data
public class Policy implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String applicationCode;

    private String policyCode;

    private String name;

    private String version;

    private String content;

    private String description;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}
