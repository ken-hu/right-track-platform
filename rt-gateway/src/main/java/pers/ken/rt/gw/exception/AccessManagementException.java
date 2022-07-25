package pers.ken.rt.gw.exception;

import pers.ken.rt.common.exception.ErrorCodeInterface;
import pers.ken.rt.common.exception.ServiceException;

/**
 * <code> AccessManagementException </code>
 * <desc> AccessManagementException </desc>
 * <b>Creation Time:</b> 2022/7/19 13:48.
 *
 * @author Ken.Hu
 */
public class AccessManagementException extends ServiceException {
    public AccessManagementException() {
    }

    public AccessManagementException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }

    public AccessManagementException(ErrorCodeInterface errorCode, String msg) {
        super(errorCode, msg);
    }

    public AccessManagementException(ErrorCodeInterface errorCode, String msg, String detail) {
        super(errorCode, msg, detail);
    }

    public AccessManagementException(ErrorCodeInterface errorCode, Throwable cause, String msg, String detail) {
        super(errorCode, cause, msg, detail);
    }

    public AccessManagementException(int code, String msg, String detail, Throwable cause) {
        super(code, msg, detail, cause);
    }
}
