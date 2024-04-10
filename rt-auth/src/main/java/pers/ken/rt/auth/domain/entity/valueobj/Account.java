package pers.ken.rt.auth.domain.entity.valueobj;

import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ken
 * @className: AccountNumber
 * @createdTime: 2023/3/10 1:34
 * @desc:
 */
public record Account(String accountNumber) {
    public Account {
        if (StringUtils.isBlank(accountNumber)) {
            throw new ValidationException("accountNumber can not be empty");
        }
    }
}
