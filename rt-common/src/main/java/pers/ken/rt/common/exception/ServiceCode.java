package pers.ken.rt.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * <name> ErrorCode </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:59.
 *
 * @author _Ken.Hu
 */
@Getter
@AllArgsConstructor
public enum ServiceCode {


    /**
     * Service Code for response
     */

    SUCCESS("Success", 0, HttpStatus.OK),
    FAILED("Failed", 1, HttpStatus.INTERNAL_SERVER_ERROR),


    INVALID_REQ("Invalid request", 40000, HttpStatus.BAD_REQUEST),

    PERMISSION_NOT_ENOUGH("Permission not enough", 20001, HttpStatus.FORBIDDEN),
    AUTHENTICATION_FAILED("Authentication failed", 20001, HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("Token invalid", 20004, HttpStatus.UNAUTHORIZED),
    ;

    private final String message;
    private final int code;
    private final HttpStatus httpStatus;
}
