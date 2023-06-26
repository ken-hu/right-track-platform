package pers.ken.rt.starter.storage.core;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * @author Ken
 * @className: OssClient
 * @createdTime: 2023/3/12 0:38
 * @desc:
 */
@Slf4j
public class OssStorageClient extends BaseStorageClient<OSS> {
    /**
     * Instantiates a new Base storage client.
     *
     * @param client the client
     */
    public OssStorageClient(OSS client) {
        super(client);
    }

    @Override
    public FileInfo upload(InputStream is, String fileKey) {
        PutObjectRequest request = new PutObjectRequest(this.bucket, fileKey, is);
        PutObjectResult result = this.client.putObject(request);
        ResponseMessage response = result.getResponse();
        long contentLength = response.getContentLength();
        String md5 = response.getHttpResponse().getFirstHeader("Content-MD5").getValue();
        return FileInfo.builder()
                .fileKey(fileKey)
                .fileMd5(String.valueOf(md5))
                .fileSize(contentLength)
                .extName(FilenameUtils.getExtension(fileKey))
                .fileUrl(StringUtils.strip(this.domain, SLASH) + SLASH + fileKey)
                .build();
    }

    @Override
    public FileInfo getMetaObject(String bucket, String fileKey) {
        LocalDateTime start = LocalDateTime.now();
        ObjectMetadata metadata = this.client.getObjectMetadata(bucket, fileKey);
        return null;
    }

    @Override
    public InputStream download(String bucket, String fileKey) {
        OSSObject object = this.client.getObject(bucket, fileKey);
        return object.getObjectContent();
    }


    @Override
    public void download(String bucket, String fileKey, String localFilePath) {
        this.client.getObject(new GetObjectRequest(bucket, fileKey), new File(localFilePath));
    }

    @Override
    public StsInfo stsAuthToken() {
        return null;
    }

//    @Override
//    public StsToken stsAuthToken(Long expireSecond, String dir, CallbackInfo callbackInfo) {
//        long expireEndTime = System.currentTimeMillis() + expireSecond * 1000;
//        Date expiration = new Date(expireEndTime);
//        PolicyConditions policyConds = new PolicyConditions();
//        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
//        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
//        String postPolicy = this.client.generatePostPolicy(expiration, policyConds);
//        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
//        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
//        String postSignature = this.client.calculatePostSignature(postPolicy);
//
//
//        String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
//        return StsToken.builder()
//                .accessKey(this.accessKeyId)
//                .policy(encodedPolicy)
//                .signature(postSignature)
//                .host(this.domain)
//                .expire(String.valueOf(expireEndTime / 1000))
//                .callback(base64CallbackBody)
//                .build();
//    }
}
