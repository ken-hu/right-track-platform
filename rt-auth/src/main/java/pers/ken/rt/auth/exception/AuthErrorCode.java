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
    /**
     * auth服务自定义异常code
     */
    PASSWORD_REUSE_NOT_ALLOWED("PasswordReuseNotAllowed", "Password verification failed"),

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
