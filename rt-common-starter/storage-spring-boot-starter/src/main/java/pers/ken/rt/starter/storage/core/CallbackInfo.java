package pers.ken.rt.starter.storage.core;

import lombok.Data;

/**
 * @author Ken
 * @className: CallbackInfo
 * @createdTime: 2023/3/14 18:04
 * @desc:
 */
@Data
public class CallbackInfo {
    private String callbackUrl;
    private String callbackBody;
    private String callbackBodyType;
}
