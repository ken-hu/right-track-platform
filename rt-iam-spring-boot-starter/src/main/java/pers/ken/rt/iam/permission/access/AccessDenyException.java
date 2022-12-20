package pers.ken.rt.iam.permission.access;

/**
 * <code> AccessDenyException </code>
 * <desc> AccessDenyException </desc>
 * <b>Creation Time:</b> 2022/8/4 16:01.
 *
 * @author Ken.Hu
 */
public class AccessDenyException extends RuntimeException{

    public AccessDenyException(String message) {
        super(message);
    }

}
