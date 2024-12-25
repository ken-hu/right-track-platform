package pers.ken.rt.starter.storage.core;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Ken
 * @className: ObsStorageClient
 * @createdTime: 2023/3/16 0:18
 * @desc:
 */
public class ObsStorageClient extends BaseStorageClient<ObsClient> {
    /**
     * Instantiates a new Base storage client.
     *
     * @param client the client
     */
    public ObsStorageClient(ObsClient client) {
        super(client);
    }

    @Override
    public FileInfo upload(InputStream is, String fileKey) {
        PutObjectResult result = this.client.putObject(this.bucket, fileKey, is);
        Map<String, Object> responseHeaders = result.getResponseHeaders();
        return FileInfo.builder()
                .fileKey(fileKey)
                .fileMd5(String.valueOf(responseHeaders.get("Content-MD5")))
                .fileSize((Long) responseHeaders.get("Content-Length"))
                .extName(FilenameUtils.getExtension(fileKey))
                .fileUrl(StringUtils.strip(this.domain, SLASH) + SLASH + fileKey)
                .build();
    }

    @Override
    public FileInfo getMetaObject(String bucket, String fileKey) {
        return null;
    }

    @Override
    public InputStream download(String bucket, String fileKey) {
        return null;
    }

    @Override
    public void download(String bucket, String fileKey, String localFilePath) {

    }

    @Override
    public StsInfo stsAuthToken() {
        return null;
    }
}
