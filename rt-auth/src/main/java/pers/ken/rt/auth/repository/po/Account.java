package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.ArrayTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName account
 */
@TableName(value = "account", autoResultMap = true)
@Data
public class Account implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private String nickname;

    private String avatar;

    private String status;

    private String registeredFrom;
    private String tenantCode;

    @TableField(value = "dept_codes", typeHandler = ArrayTypeHandler.class, jdbcType = JdbcType.ARRAY)
    private String[] deptCodes;

    private LocalDateTime registeredAt;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}
