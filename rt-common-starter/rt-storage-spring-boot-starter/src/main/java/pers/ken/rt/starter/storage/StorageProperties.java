package pers.ken.rt.starter.storage;

import lombok.Data;


/**
 * @author Ken
 * @className: StorageProperties
 * @createdTime: 2023/3/11 15:32
 * @desc:
 */
@Data
public abstract class StorageProperties {
    private boolean enabled;
    private String domain;
    private String region;
    private String bucket;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String prefix;
}
