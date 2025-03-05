package pers.ken.rt.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String code;
    private String error;
    private String target;
    private List<ErrorDetails> errorDetails;


    @Data
    @AllArgsConstructor
    public static class ErrorDetails {
        private String code;
        private String target;
        private String message;
    }

    private ErrorResponse(String code, String error) {
        this.code = code;
        this.error = error;
    }

    public static ErrorResponse of(ErrorCodeInterface code, String message) {
        return new ErrorResponse(code.getCode(), message);
    }

    public static ErrorResponse of(ErrorCodeInterface code, String message, String target, List<ErrorDetails> errorDetails) {
        return new ErrorResponse(code.getCode(), message, target, errorDetails);
    }

    public static ErrorResponse of(ErrorCodeInterface code, String target, List<ErrorDetails> errorDetails) {
        return new ErrorResponse(code.getCode(), code.getMessage(), target, errorDetails);
    }

}
