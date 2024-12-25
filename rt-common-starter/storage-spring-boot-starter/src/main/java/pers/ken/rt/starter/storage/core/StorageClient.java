package pers.ken.rt.starter.storage.core;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * The interface Storage client.
 *
 * @author Ken
 * @className: StorageClient
 * @createdTime: 2023 /3/11 23:02
 * @desc:
 */
public interface StorageClient {

    /**
     * Upload file info.
     *
     * @param file the file
     * @return the file info
     */
    FileInfo upload(MultipartFile file);

    /**
     * Upload file info.
     *
     * @param file    the file
     * @param fileKey the file key
     * @return the file info
     */
    FileInfo upload(MultipartFile file, String fileKey);

    /**
     * Upload file info.
     *
     * @param file the file
     * @return the file info
     */
    FileInfo upload(File file);

    /**
     * Upload file info.
     *
     * @param file    the file
     * @param fileKey the file key
     * @return the file info
     */
    FileInfo upload(File file, String fileKey);

    /**
     * Upload file info.
     *
     * @param is      the is
     * @param fileKey the file key
     * @return the file info
     */
    FileInfo upload(InputStream is, String fileKey);

    /**
     * Gets meta object.
     *
     * @param bucket  the bucket
     * @param fileKey the file key
     * @return the meta object
     */
    FileInfo getMetaObject(String bucket, String fileKey);


    /**
     * Download input stream.
     *
     * @param bucket  the bucket
     * @param fileKey the file key
     * @return the input stream
     */
    InputStream download(String bucket, String fileKey);

    /**
     * Download input stream.
     *
     * @param bucket        the bucket
     * @param fileKey       the file key
     * @param localFilePath the local file path
     * @return the input stream
     */
    void download(String bucket, String fileKey, String localFilePath);

    /**
     * Sts auth token.
     *
     * @return the sts token
     */
    StsInfo stsAuthToken();
}
