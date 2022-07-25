package pers.ken.rt.iam.exception;

import pers.ken.rt.common.exception.ErrorCodeInterface;
import pers.ken.rt.common.exception.ServiceException;

/**
 * <code> AccessDenyException </code>
 * <desc> AccessDenyException </desc>
 * <b>Creation Time:</b> 2022/7/25 11:30.
 *
 * @author Ken.Hu
 */
public class AccessDenyException extends ServiceException {
    public AccessDenyException() {
    }

    public AccessDenyException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }

    public AccessDenyException(ErrorCodeInterface errorCode, String msg) {
        super(errorCode, msg);
    }

    public AccessDenyException(ErrorCodeInterface errorCode, String msg, String detail) {
        super(errorCode, msg, detail);
    }

    public AccessDenyException(ErrorCodeInterface errorCode, Throwable cause, String msg, String detail) {
        super(errorCode, cause, msg, detail);
    }

    public AccessDenyException(int code, String msg, String detail, Throwable cause) {
        super(code, msg, detail, cause);
    }
}
