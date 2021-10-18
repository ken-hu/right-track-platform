package pers.ken.rt.uc.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

/**
 * <name> User </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:30.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_user")
@Data
public class OauthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private OauthUserStatus status;

    @Getter
    public enum OauthUserStatus {
        /**
         * 启用
         */
        ENABLE,
        /**
         * 禁用
         */
        DISABLE
    }
}
