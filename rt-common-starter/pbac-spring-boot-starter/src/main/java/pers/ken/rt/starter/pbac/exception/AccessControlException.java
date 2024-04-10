package pers.ken.rt.starter.pbac.exception;

/**
 * @ClassName: PermissionException
 * @Created: 2023/11/8 17:24
 * @Desc:
 * @Author Ken
 */
public abstract class AccessControlException extends RuntimeException {

    private AccessDeniedInfo deniedInfo;

    public AccessControlException(AccessDeniedInfo deniedInfo, String message) {
        super(message);
    }

    public AccessControlException(AccessDeniedInfo deniedInfo, String message, Throwable cause) {
        super(message, cause);
    }
}
