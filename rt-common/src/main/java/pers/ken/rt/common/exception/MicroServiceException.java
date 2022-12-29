package pers.ken.rt.common.exception;

/**
 * <code> RemoteException </code>
 * <desc> RemoteException </desc>
 * <b>Creation Time:</b> 2021/10/19 0:06.
 *
 * @author _Ken.Hu
 */
public class MicroServiceException extends ServiceException {
    public MicroServiceException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }

}
