package pers.ken.rt.starter.pbac.exception;

/**
 * The type Access control exception.
 *
 * @ClassName: PermissionException
 * @Created: 2023 /11/8 17:24
 * @Desc:
 * @Author Ken
 */
public abstract class BaseAccessManagerException extends RuntimeException {

    public BaseAccessManagerException(String message) {
        super(message);
    }
}
