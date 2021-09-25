package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pers.ken.rt.common.exception.ServiceCode;

/**
 * <name> PlatformError </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 21:43.
 *
 * @author _Ken.Hu
 */
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class PlatformError {
    private int code;
    private String message;
    private String detail;
    private String help;
    private String uri;

    public PlatformError(ServiceCode serviceCode) {
        this.code = serviceCode.getCode();
        this.message = serviceCode.getMessage();
    }

    public PlatformError(ServiceCode serviceCode, String uri, String detail) {
        this.code = serviceCode.getCode();
        this.message = serviceCode.getMessage();
        this.detail = detail;
        this.uri = uri;
    }


    public PlatformError(ServiceCode serviceCode, String detail) {
        this.code = serviceCode.getCode();
        this.message = serviceCode.getMessage();
        this.detail = detail;
    }
}
