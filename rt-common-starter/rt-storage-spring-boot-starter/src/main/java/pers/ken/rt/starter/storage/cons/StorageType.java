package pers.ken.rt.starter.storage.cons;

import lombok.Getter;

/**
 * @author Ken
 * @className: StorageType
 * @createdTime: 2023/3/11 23:15
 * @desc:
 */
@Getter
public class StorageType {
    private StorageType() {
    }
    private static final String FILE_STORAGE = "file.storage";
    private static final String ENABLE = ".enable";
    public static final String OSS = FILE_STORAGE + ".oss";
    public static final String OSS_ENABLE = OSS + ENABLE;
    public static final String OBS = FILE_STORAGE + ".obs";
    public static final String OBS_ENABLE = OBS + ENABLE;
    public static final String S3 = FILE_STORAGE + ".s3";
    public static final String S3_ENABLE = S3 + ENABLE;
    public static final String CBS = FILE_STORAGE + ".cbs";
    public static final String CBS_ENABLE = CBS + ENABLE;
}
