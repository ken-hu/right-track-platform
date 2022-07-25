package pers.ken.rt.uaa.iam.exception;

import pers.ken.rt.common.exception.ErrorCodeInterface;
import pers.ken.rt.common.exception.ServiceException;

/**
 * <code> IAMAuthException </code>
 * <desc> IAMAuthException </desc>
 * <b>Creation Time:</b> 2022/6/27 9:55.
 *
 * @author Ken.Hu
 */
public class IAMAuthException extends ServiceException {
    public IAMAuthException() {
    }

    public IAMAuthException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }

    public IAMAuthException(ErrorCodeInterface errorCode, String msg) {
        super(errorCode, msg);
    }

    public IAMAuthException(ErrorCodeInterface errorCode, String msg, String detail) {
        super(errorCode, msg, detail);
    }

    public IAMAuthException(ErrorCodeInterface errorCode, Throwable cause, String msg, String detail) {
        super(errorCode, cause, msg, detail);
    }

    public IAMAuthException(int code, String msg, String detail, Throwable cause) {
        super(code, msg, detail, cause);
    }
}
