package pers.ken.rt.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <name> ErrorCode </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:59.
 *
 * @author _Ken.Hu
 */
@Getter
@AllArgsConstructor
public enum ServiceCode implements ErrorCodeInterface {


    /**
     * Define for base service code
     */
    SUCCESS("Success", 0),
    FAILED("Failed", 1),
    INVALID_REQ("Invalid request", 1001),
    PERMISSION_NOT_ENOUGH("Permission not enough", 1002),
    AUTHENTICATION_FAILED("Authentication failed", 1003),
    TOKEN_INVALID("Token invalid", 1003),
    MICRO_SERVICE_FALL_BACK("Micro service fall back", 1004),

    ACCESS_DENY("Access Deny", 1005);


    private final String message;
    private final int code;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getDetail() {
        return this.message;
    }
}
