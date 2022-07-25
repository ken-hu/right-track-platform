package pers.ken.rt.iam.internal;

/**
 * <code> PolicyGetException </code>
 * <desc> PolicyGetException </desc>
 * <b>Creation Time:</b> 2022/8/24 10:10.
 *
 * @author Ken.Hu
 */
public class PolicyGetException extends RuntimeException{
    public PolicyGetException() {
    }

    public PolicyGetException(String message) {
        super(message);
    }

    public PolicyGetException(String message, Throwable cause) {
        super(message, cause);
    }

    public PolicyGetException(Throwable cause) {
        super(cause);
    }

    public PolicyGetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
