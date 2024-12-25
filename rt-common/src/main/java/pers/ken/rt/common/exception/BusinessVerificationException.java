package pers.ken.rt.common.exception;

/**
 * @ClassName: BusinessVerificationException
 * @Created: 2024/12/23 10:51
 * @Author ken
 */
public class BusinessVerificationException extends ServiceException {
    public BusinessVerificationException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }

    public BusinessVerificationException(ErrorCodeInterface errorCode, String errorDesc) {
        super(errorCode, errorDesc);
    }
}
