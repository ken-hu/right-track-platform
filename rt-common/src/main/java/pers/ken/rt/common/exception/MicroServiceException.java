package pers.ken.rt.common.exception;

/**
 * <code> RemoteException </code>
 * <desc> RemoteException </desc>
 * <b>Creation Time:</b> 2021/10/19 0:06.
 *
 * @author _Ken.Hu
 */
public class MicroServiceException extends ServiceException {

    public MicroServiceException() {
    }

    public MicroServiceException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }

    public MicroServiceException(ErrorCodeInterface errorCode, String msg) {
        super(errorCode, msg);
    }

    public MicroServiceException(ErrorCodeInterface errorCode, String msg, String detail) {
        super(errorCode, msg, detail);
    }

    public MicroServiceException(ErrorCodeInterface errorCode, Throwable cause, String msg, String detail) {
        super(errorCode, cause, msg, detail);
    }

    public MicroServiceException(int code, String msg, String detail, Throwable cause) {
        super(code, msg, detail, cause);
    }
}
