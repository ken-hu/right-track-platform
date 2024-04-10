package pers.ken.rt.auth.domain.entity.valueobj;

import lombok.Getter;

/**
 * @author Ken
 * @className: UserStatus
 * @createdTime: 2023/3/7 19:08
 * @desc:
 */
@Getter
public enum UserStatus {

    /**
     * 禁用/启用
     */
    ENABLE,
    DISABLE;

    public static UserStatus convert(String code) {
        for (UserStatus value : UserStatus.values()) {
            if (value.name().equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("UserStatus [%s] not exist", code));
    }
}
