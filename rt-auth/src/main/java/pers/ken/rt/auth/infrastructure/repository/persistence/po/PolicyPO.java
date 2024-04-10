package pers.ken.rt.auth.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName policy
 */
@TableName(value = "policy", autoResultMap = true)
@Data
public class PolicyPO implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private String name;

    /**
     *
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JsonNode policyDocument;

    /**
     *
     */
    private LocalDateTime updateTime;

    /**
     *
     */
    private String versionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}