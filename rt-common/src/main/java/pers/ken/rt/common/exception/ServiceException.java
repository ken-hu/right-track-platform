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
        this.msg = errorCode.getMessage();
    }

    public ServiceException(ErrorCodeInterface errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
        this.msg = msg;
        this.detail = msg;
    }

    public ServiceException(ErrorCodeInterface errorCode, String msg, String detail) {
        super(msg);
        this.code = errorCode.getCode();
        this.msg = msg;
        this.detail = detail;
    }


    public ServiceException(ErrorCodeInterface errorCode, Throwable cause, String msg, String detail) {
        super(msg, cause);
        this.code = errorCode.getCode();
        this.msg = msg;
        this.detail = detail;
    }

    public ServiceException(int code, String msg, String detail, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public String msg;
    public String detail;
    public int code;
}
