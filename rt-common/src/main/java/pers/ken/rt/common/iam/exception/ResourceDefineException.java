package pers.ken.rt.common.iam.exception;

/**
 * <code> ResourceDefineException </code>
 * <desc> ResourceDefineException </desc>
 * <b>Creation Time:</b> 2022/3/8 22:22.
 *
 * @author _Ken.Hu
 */
public class ResourceDefineException extends RuntimeException{
    public ResourceDefineException() {
    }

    public ResourceDefineException(String message) {
        super(message);
    }

    public ResourceDefineException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceDefineException(Throwable cause) {
        super(cause);
    }

    public ResourceDefineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
