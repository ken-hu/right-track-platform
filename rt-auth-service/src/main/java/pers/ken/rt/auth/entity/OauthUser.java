package pers.ken.rt.auth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_policy_rel",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "policy_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))}
    )
    private List<Policy> policies = new ArrayList<>();

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
