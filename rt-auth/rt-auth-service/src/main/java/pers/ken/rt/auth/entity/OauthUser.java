package pers.ken.rt.auth.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * <name> User </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:30.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OauthUser oauthUser = (OauthUser) o;
        return id != null && Objects.equals(id, oauthUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
