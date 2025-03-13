package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName third_account
 */
@TableName(value = "third_account")
@Data
public class ThirdAccount implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String registrationId;
    private Integer accountId;

    private String uniqueId;

    private String username;

    private String credentials;

    private LocalDateTime credentialsExpiresAt;

    private String type;

    private String nickname;

    private String ext;

    private String avatarUrl;

    private LocalDateTime registeredAt;
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;


    private static final long serialVersionUID = 1L;
}
