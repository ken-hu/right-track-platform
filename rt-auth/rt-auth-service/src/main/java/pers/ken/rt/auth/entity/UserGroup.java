package pers.ken.rt.auth.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * <code> UserGroup </code>
 * <desc> UserGroup </desc>
 * <b>Creation Time:</b> 2022/2/24 22:41.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "user_group")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserGroup userGroup = (UserGroup) o;
        return id != null && Objects.equals(id, userGroup.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
