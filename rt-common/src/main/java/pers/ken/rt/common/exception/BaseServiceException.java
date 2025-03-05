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
public abstract class BaseServiceException extends RuntimeException {

    public BaseServiceException() {
        super();
    }

    public BaseServiceException(ErrorCodeInterface errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseServiceException(ErrorCodeInterface errorCode, String errorDesc) {
        super(errorDesc);
        this.errorCode = errorCode;
    }


    private ErrorCodeInterface errorCode;

}
