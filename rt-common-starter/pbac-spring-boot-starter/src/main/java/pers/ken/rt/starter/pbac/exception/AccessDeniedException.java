package pers.ken.rt.starter.pbac.exception;

/**
 * <code> AccessDenyException </code>
 * <desc> AccessDenyException </desc>
 * <b>Creation Time:</b> 2022/8/4 16:01.
 *
 * @author Ken.Hu
 */
public class AccessDeniedException extends AccessControlException {

    public AccessDeniedException(String message) {
        super(message);
    }

}
