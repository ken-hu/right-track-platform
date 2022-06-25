package pers.ken.rt.common.iam.exception;

import pers.ken.rt.common.exception.ErrorCodeInterface;
import pers.ken.rt.common.exception.ServiceCode;
import pers.ken.rt.common.exception.ServiceException;

/**
 * <code> PermissionNotEnoughException </code>
 * <desc> PermissionNotEnoughException </desc>
 * <b>Creation Time:</b> 2022/3/27 13:11.
 *
 * @author _Ken.Hu
 */
public class PermissionNotEnoughException extends ServiceException {


    public PermissionNotEnoughException() {
    }

    public PermissionNotEnoughException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }

    public PermissionNotEnoughException(ErrorCodeInterface errorCode, String msg) {
        super(errorCode, msg);
    }

    public PermissionNotEnoughException(ErrorCodeInterface errorCode, String msg, String detail) {
        super(errorCode, msg, detail);
    }

    public PermissionNotEnoughException(ErrorCodeInterface errorCode, Throwable cause, String msg, String detail) {
        super(errorCode, cause, msg, detail);
    }

    public PermissionNotEnoughException(int code, String msg, String detail, Throwable cause) {
        super(code, msg, detail, cause);
    }
}
