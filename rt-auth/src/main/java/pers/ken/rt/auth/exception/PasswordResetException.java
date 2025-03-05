package pers.ken.rt.auth.exception;

import pers.ken.rt.common.exception.BaseServiceException;
import pers.ken.rt.common.exception.ErrorCodeInterface;

/**
 * @ClassName: RestPasswordException
 * @Created: 2024/12/11 16:39
 * @Author ken
 */
public class PasswordResetException extends BaseServiceException {
    public PasswordResetException(ErrorCodeInterface errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
