package pers.ken.rt.iam.internal;

/**
 * <code> ResourceResolveException </code>
 * <desc> ResourceResolveException </desc>
 * <b>Creation Time:</b> 2022/8/9 14:05.
 *
 * @author Ken.Hu
 */
public class ResourceResolveException extends RuntimeException{
    public ResourceResolveException() {
    }

    public ResourceResolveException(String message) {
        super(message);
    }

    public ResourceResolveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceResolveException(Throwable cause) {
        super(cause);
    }

    public ResourceResolveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
