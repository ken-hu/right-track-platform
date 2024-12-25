package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pers.ken.rt.common.exception.ErrorCodeInterface;

import java.util.List;

/**
 * <name> PlatformError </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 21:43.
 *
 * @author _Ken.Hu
 */
@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String error;
    private String target;
    // todo innerErrors
    private List<InnerError> innerErrors;


    @Data
    static class InnerError {
        private String code;
        private String message;
    }

    private ErrorResponse(String code, String error) {
        this.code = code;
        this.error = error;
    }

    public static ErrorResponse of(ErrorCodeInterface code, String message) {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(code.getCode()), code.getMessage());
        errorResponse.setError(message);
        return errorResponse;
    }

}
