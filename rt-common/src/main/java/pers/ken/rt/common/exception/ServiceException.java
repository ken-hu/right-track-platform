package pers.ken.rt.common.exception;


import lombok.Getter;

/**
 * <name> ErrorException </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:51.
 *
 * @author _Ken.Hu
 */
@Getter
public abstract class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(ErrorCodeInterface errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCodeInterface errorCode, String errorDesc) {
        super(errorDesc);
        this.errorCode = errorCode;
    }


    private ErrorCodeInterface errorCode;

}
