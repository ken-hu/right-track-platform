package pers.ken.rt.auth.domain.entity.valueobj;

import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ken
 * @className: Email
 * @createdTime: 2023/3/8 0:47
 * @desc:
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {
    private static final String EMAIL_PATTERN = "^0?[1-9]{2,3}-?\\d{8}$";

    private String email;

    public Email(String email) {
        this.email = email;
        if (StringUtils.isBlank(email) || !isValid(email)) {
            throw new ValidationException("Illegal phone number");
        }
    }

    public static boolean isValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }
}
