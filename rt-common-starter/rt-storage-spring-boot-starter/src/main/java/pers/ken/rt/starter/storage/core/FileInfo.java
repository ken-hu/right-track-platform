package pers.ken.rt.starter.storage.core;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ken
 * @className: FileInfo
 * @createdTime: 2023/3/14 1:24
 * @desc:
 */
@Data
@Builder
public class FileInfo implements Serializable {
    String fileKey;
    String fileName;
    String extName;
    Long fileSize;
    String mode;
    String fileMd5;
    String fileUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
