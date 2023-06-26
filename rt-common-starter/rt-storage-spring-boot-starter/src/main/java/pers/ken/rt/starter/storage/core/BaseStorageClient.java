package pers.ken.rt.starter.storage.core;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * The type Base storage client.
 *
 * @param <T> the type parameter
 * @author Ken
 * @className: ObsStorageClient
 * @createdTime: 2023 /3/12 0:34
 * @desc:
 */
public abstract class BaseStorageClient<T> implements StorageClient {
    /**
     * The Client.
     */
    protected T client;
    /**
     * The Domain.
     */
    protected String domain;
    /**
     * The Region.
     */
    protected String region;
    /**
     * The Bucket.
     */
    protected String bucket;
    /**
     * The Endpoint.
     */
    protected String endpoint;
    /**
     * The Access key id.
     */
    protected String accessKeyId;
    /**
     * The Access key secret.
     */
    protected String accessKeySecret;
    /**
     * The Prefix.
     */
    protected String prefix;

    /**
     * The constant SLASH.
     */
    protected static final String SLASH = "/";
    /**
     * The constant DEFAULT_PART_SIZE.
     */
    protected static final long DEFAULT_PART_SIZE = 1024 * 1024L;
    /**
     * The constant DASH.
     */
    protected static final String DASH = "-";
    /**
     * The constant DOT.
     */
    protected static final String DOT = ".";
    /**
     * The constant TAG.
     */
    protected static final String TAG = "PartETag";

    /**
     * Instantiates a new Base storage client.
     *
     * @param client the client
     */
    public BaseStorageClient(T client) {
        this.client = client;
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        return this.upload(file, file.getOriginalFilename());
    }

    @SneakyThrows
    @Override
    public FileInfo upload(MultipartFile file, String fileKey) {
        return this.upload(file.getInputStream(), fileKey);
    }

    @Override
    public FileInfo upload(File file) {
        return this.upload(file, file.getName());
    }

    @SneakyThrows
    @Override
    public FileInfo upload(File file, String fileKey) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            return this.upload(is, file.getName());
        }
    }

    protected String createNewFileName(String fileName) {
        String ext = FilenameUtils.getExtension(fileName);
        return RandomStringUtils.randomAlphabetic(16) + ext;
    }


    /**
     * Upload input stream file info.
     *
     * @param is      the is
     * @param fileKey the file key
     * @return the file info
     */
    public abstract FileInfo upload(InputStream is, String fileKey);
}
