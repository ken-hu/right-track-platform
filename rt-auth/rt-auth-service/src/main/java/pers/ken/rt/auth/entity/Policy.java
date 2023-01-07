package pers.ken.rt.auth.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pers.ken.rt.auth.common.JsonbTypeHandler;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <code> PolicyDocument </code>
 * <desc> PolicyDocument </desc>
 * <b>Creation Time:</b> 2022/2/24 22:43.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "policy")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonbTypeHandler.class)
@EntityListeners(AuditingEntityListener.class)
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode policyDocument;
    private String versionId;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Policy that = (Policy) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
