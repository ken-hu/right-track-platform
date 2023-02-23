package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Builder.Default
    private boolean succeed = Boolean.FALSE;
    private Integer code;
    private String message;
    private String detail;
    private String requestId;
    private String path;

    public PlatformError(Integer code, String message, String detail, String requestId, String path) {
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.requestId = requestId;
        this.path = path;
    }

    public PlatformError(Integer code, String message, String requestId, String path) {
        this.code = code;
        this.message = message;
        this.detail = message;
        this.requestId = requestId;
        this.path = path;
    }
}
