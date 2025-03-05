package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private static final long serialVersionUID = 1L;
}
