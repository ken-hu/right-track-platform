package pers.ken.rt.auth.domain.entity.valueobj;

import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ken
 * @className: PhoneNumber
 * @createdTime: 2023/3/8 0:43
 * @desc:
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNumber {
    private static final String PHONE_PATTERN = "^0?[1-9]{2,3}-?\\d{8}$";
    private String phone;

    public PhoneNumber(String phone) {
        this.phone = phone;
        if (StringUtils.isBlank(phone) || !isValid(phone)) {
            throw new ValidationException("Illegal phone number");
        }
    }

    public static boolean isValid(String phone) {
        return phone.matches(PHONE_PATTERN);
    }
}
