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


    /**
     * Authorization & permission
     */
    AUTHENTICATION_FAILED("Authentication failed", 20000),
    PERMISSION_NOT_ENOUGH("Permission not enough", 20001),
    TOKEN_INVALID("Token invalid", 20002),


    /**
     * Parameters check
     */
    INVALID_PARAMETERS("Invalid Parameters", 40000),
    MISSING_PARAMETERS("Missing Parameters", 40001),
    MISSING_DATA("Data not existed", 40002),
    API_NOT_FOUND("Api not found", 40003),


    /**
     * Business
     */
    BUSINESS_FAILED("Business Failed", 50000),

    /**
     * Rest api error
     */
    INTERNAL_ERROR("Internal service failed", 60000),
    EXTERNAL_ERROR("External service failed", 60001),

    ;



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
