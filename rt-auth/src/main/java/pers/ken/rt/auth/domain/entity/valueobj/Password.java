package pers.ken.rt.auth.domain.entity.valueobj;

import lombok.Getter;

/**
 * @author Ken
 * @className: Password
 * @createdTime: 2023/3/10 1:34
 * @desc:
 */
@Getter
public class Password {
    private String password;

    public Password(String password) {
        this.password = password;
        //TODO check password
    }
}
