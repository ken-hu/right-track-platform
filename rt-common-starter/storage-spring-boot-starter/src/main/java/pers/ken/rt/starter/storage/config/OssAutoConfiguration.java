package pers.ken.rt.starter.storage.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import pers.ken.rt.starter.storage.cons.StorageType;
import pers.ken.rt.starter.storage.core.OssStorageClient;
import pers.ken.rt.starter.storage.core.StorageClient;

/**
 * @author Ken
 * @className: ObsAutoConfiguration
 * @createdTime: 2023/3/11 23:24
 * @desc:
 */
@Import(OssProperties.class)
@ConditionalOnProperty(value = StorageType.OSS_ENABLE, havingValue = "true")
public class OssAutoConfiguration {
    @Primary
    @Bean
    StorageClient init(OssProperties properties) {
        OSSClientBuilder clientBuilder = new OSSClientBuilder();
        OSS client = clientBuilder.build(properties.getEndpoint(),
                CredentialsProviderFactory
                        .newDefaultCredentialProvider(properties.getAccessKeyId(),
                                properties.getAccessKeySecret())
        );
        boolean bucketExist = client.doesBucketExist(properties.getBucket());
        if (!bucketExist) {
            client.createBucket(properties.getBucket());
        }
        return new OssStorageClient(client);
    }
}
