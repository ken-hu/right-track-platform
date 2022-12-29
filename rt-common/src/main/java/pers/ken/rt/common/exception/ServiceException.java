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
        this.code = errorCode.getCode();
        this.detail = errorCode.getMessage();
    }

    public ServiceException(ErrorCodeInterface errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.detail = message;
    }

    public ServiceException(ErrorCodeInterface errorCode, String message, String detail) {
        super(message);
        this.code = errorCode.getCode();
        this.detail = detail;
    }

    public ServiceException(ErrorCodeInterface errorCode, Throwable cause, String message, String detail) {
        super(message, cause);
        this.code = errorCode.getCode();
        this.detail = detail;
    }

    public String detail;
    public int code;
}
