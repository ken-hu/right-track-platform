package pers.ken.rt.auth.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private String appCode;

    private String policyCode;

    private String name;

    private String version;

    private String content;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}
