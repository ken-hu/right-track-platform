package pers.ken.rt.starter.storage.config;

import com.obs.services.ObsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import pers.ken.rt.starter.storage.cons.StorageType;
import pers.ken.rt.starter.storage.core.StorageClient;
import pers.ken.rt.starter.storage.core.ObsStorageClient;

/**
 * @author Ken
 * @className: ObsAutoConfiguration
 * @createdTime: 2023/3/11 23:24
 * @desc:
 */
@Import(ObsProperties.class)
@ConditionalOnProperty(value = StorageType.OBS_ENABLE, havingValue = "true")
public class ObsAutoConfiguration {

    @Primary
    @Bean
    StorageClient init(ObsProperties properties) {
        ObsClient obsClient = new ObsClient(properties.getAccessKeyId(),
                properties.getAccessKeySecret(),
                properties.getEndpoint());
        return new ObsStorageClient(obsClient);
    }
}
