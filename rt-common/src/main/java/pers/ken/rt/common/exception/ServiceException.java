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

    ServiceException(ServiceCode serviceCode) {
        this.serviceCode = serviceCode;
        this.message = serviceCode.getMessage();
    }

    ServiceException(ServiceCode serviceCode, String message) {
        this.serviceCode = serviceCode;
        this.message = message;
    }

    protected final ServiceCode serviceCode;
    protected final String message;

}
