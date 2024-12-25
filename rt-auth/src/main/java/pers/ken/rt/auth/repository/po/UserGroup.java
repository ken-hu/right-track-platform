package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private Integer tenantId;
    private String tenantCode;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}