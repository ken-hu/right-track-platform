package pers.ken.rt.iam.exception;

import lombok.AllArgsConstructor;
import pers.ken.rt.common.exception.ErrorCodeInterface;

/**
 * <code> AccessErrorCode </code>
 * <desc> AccessErrorCode </desc>
 * <b>Creation Time:</b> 2022/7/25 11:30.
 *
 * @author Ken.Hu
 */
@AllArgsConstructor
public enum AccessErrorCode implements ErrorCodeInterface {

    /**
     *
     */
    ACCESS_DENY("拒绝访问", 40001);

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
