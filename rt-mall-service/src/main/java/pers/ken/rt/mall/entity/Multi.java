package pers.ken.rt.mall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @ClassName: RegionRepository
 * @CreatedTime: 2023/1/16 18:36
 * @Desc:
 * @Author Ken
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "multi")
@EntityListeners(AuditingEntityListener.class)
public class Multi {
    @Id
    private Long id;
    private String name;
    private Long adcode;
    private Long categoryCode;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Multi that = (Multi) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
