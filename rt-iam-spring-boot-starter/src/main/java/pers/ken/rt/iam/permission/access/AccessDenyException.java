package pers.ken.rt.iam.permission.access;

/**
 * <code> AccessDenyException </code>
 * <desc> AccessDenyException </desc>
 * <b>Creation Time:</b> 2022/8/4 16:01.
 *
 * @author Ken.Hu
 */
public class AccessDenyException extends RuntimeException{
    public AccessDenyException() {
    }

    public AccessDenyException(String message) {
        super(message);
    }

    public AccessDenyException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDenyException(Throwable cause) {
        super(cause);
    }

    public AccessDenyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
