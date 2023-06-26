package pers.ken.rt.auth.domain.entity.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * @author Ken
 * @className: UserGroup
 * @createdTime: 2023/3/7 19:04
 * @desc:
 */
@Getter
@Builder
@AllArgsConstructor
public class Group {
    private Long id;
    private String name;
    public Group(String name) {
        Assert.hasText(name, "roleName must not empty");
        this.name = name;
    }

    public static void main(String[] args) {

    }
}
