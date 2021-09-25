package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public class PlatformResult<T> {
    private int code;
    private String message;
    private T data;

    public PlatformResult(ServiceCode serviceCode) {
        this.code = serviceCode.getCode();
        this.message = serviceCode.getMessage();
    }

    public PlatformResult(ServiceCode serviceCode, T data) {
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
