package pers.ken.rt.auth.exception;

import lombok.AllArgsConstructor;
import pers.ken.rt.common.exception.ErrorCodeInterface;

/**
 * @ClassName: AuthErrorCode
 * @Created: 2024/12/11 19:41
 * @Author ken
 */
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCodeInterface {
    PASSWORD_VERIFICATION_FAILED("PasswordVerificationFailed", "Password verification failed"),

    ;


    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
