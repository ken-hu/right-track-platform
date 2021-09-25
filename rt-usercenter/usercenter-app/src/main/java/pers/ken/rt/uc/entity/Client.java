package pers.ken.rt.uc.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <name> UcClient </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:19.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;

    /**
     * token有效期
     */
    private Integer accessTokenValiditySeconds;

    private String additionalInformation;

    /**
     * 授权信息
     */
    private String authorities;

    /**
     * 授权类型
     */
    private String authorizedGrantTypes;

    private String autoApprove;

    /**
     * 密码
     */
    private String clientSecret;

    /**
     * refreshToken有效期
     */
    private Integer refreshTokenValiditySeconds;

    private String registeredRedirectUri;

    /**
     * 资源IDs
     */
    private String resourceIds;

    /**
     * 生效范围
     */
    private String scope;

    /**
     * 密码是否必填
     */
    private String secretRequired;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人ID
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人ID
     */
    private Long updateUser;
}
