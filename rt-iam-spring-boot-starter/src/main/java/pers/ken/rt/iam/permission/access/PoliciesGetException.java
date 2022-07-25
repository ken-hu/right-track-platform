package pers.ken.rt.iam.permission.access;

/**
 * <code> PoliciesGetException </code>
 * <desc> PoliciesGetException </desc>
 * <b>Creation Time:</b> 2022/8/22 17:02.
 *
 * @author Ken.Hu
 */
public class PoliciesGetException extends RuntimeException{
    public PoliciesGetException() {
    }

    public PoliciesGetException(String message) {
        super(message);
    }

    public PoliciesGetException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoliciesGetException(Throwable cause) {
        super(cause);
    }

    public PoliciesGetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
