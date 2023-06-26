package pers.ken.rt.auth.domain.entity.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * @author Ken
 * @className: Role
 * @createdTime: 2023/3/7 21:37
 * @desc:
 */
@Getter
@Builder
@AllArgsConstructor
public class Role {
    private Long id;
    private String name;

    public Role(String name) {
        Assert.hasText(name, "roleName must not empty");
        this.name = name;
    }
}
