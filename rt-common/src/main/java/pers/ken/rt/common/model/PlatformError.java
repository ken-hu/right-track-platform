package pers.ken.rt.common.model;

import lombok.*;

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
@NoArgsConstructor
public class PlatformError {
    private Boolean isSuccess = false;
    private Integer code;
    private String message;
    private String detail;
    private String requestId;
    private String uri;

    public PlatformError(Integer code, String message, String detail, String requestId, String uri) {
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.requestId = requestId;
        this.uri = uri;
    }

    public PlatformError(Integer code, String message, String requestId, String uri) {
        this.code = code;
        this.message = message;
        this.detail = message;
        this.requestId = requestId;
        this.uri = uri;
    }
}
