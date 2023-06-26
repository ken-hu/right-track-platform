package pers.ken.rt.starter.storage.core;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ken
 * @className: StsToken
 * @createdTime: 2023/3/14 17:57
 * @desc:
 */
@Data
@Builder
public class StsInfo {
    private String accessKey;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String expire;
    private String callback;
}
