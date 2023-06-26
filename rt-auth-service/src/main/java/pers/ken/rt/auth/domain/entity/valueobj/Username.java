package pers.ken.rt.auth.domain.entity.valueobj;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author Ken
 * @className: UserName
 * @createdTime: 2023/3/11 1:08
 * @desc:
 */
@Getter
public class Username {
    private String name;

    public Username(String name) {
        if (StringUtils.isBlank(name)) {
            this.name = UUID.randomUUID().toString();
        }
    }
}
