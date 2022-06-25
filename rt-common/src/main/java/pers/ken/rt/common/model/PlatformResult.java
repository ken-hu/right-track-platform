package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.ken.rt.common.exception.ErrorCodeInterface;
import pers.ken.rt.common.exception.ServiceCode;

/**
 * <name> PlatformResult </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 17:17.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatformResult<T> {
    private int code;
    private String message;
    private String requestId;
    private T data;


    public PlatformResult(ErrorCodeInterface serviceCode) {
        this.code = serviceCode.getCode();
        this.message = serviceCode.getMessage();
    }

    public PlatformResult(ErrorCodeInterface serviceCode, T data) {
        this.code = serviceCode.getCode();
        this.message = serviceCode.getMessage();
        this.data = data;
    }


    public static <T> PlatformResult<T> ok(T data) {
        return new PlatformResult<>(ServiceCode.SUCCESS, data);
    }

    public static <T> PlatformResult<T> ok() {
        return new PlatformResult<>(ServiceCode.SUCCESS);
    }
}
