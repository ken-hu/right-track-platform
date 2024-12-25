package pers.ken.rt.common.exception;

import lombok.AllArgsConstructor;

/**
 * <name> ErrorCode </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:59.
 *
 * @author _Ken.Hu
 */
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeInterface {


    /**
     * Define for base service code
     */
    FAILED("ServerError", "System error"),


    /**
     * Authorization & permission
     */
    AUTHENTICATION_FAILED("AuthenticationFailed", "Unauthorized identity access"),
    ACCESS_DENY("AccessDeny", "Access denied"),
    TOKEN_INVALID("TokenInvalid", "Token is invalid"),


    /**
     * Parameters check
     */
    INVALID_PARAMETERS("InvalidParameters", "Please check request parameters"),
    MISSING_PARAMETERS("MissingParameters", "Please check request parameters"),
    RESOURCE_NOT_FOUND("ResourcesNotFound", "The resource could not be found"),


    /**
     * Business
     */
    BUSINESS_ERROR("BusinessError", "Business verification exception"),

    /**
     * Rest api error
     */
    INTERNAL_ERROR("InternalServiceError", "Internal call exception"),
    EXTERNAL_ERROR("ExternalServiceError", "External call exception"),

    ;


    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
