package pers.ken.rt.starter.storage.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pers.ken.rt.starter.storage.StorageProperties;
import pers.ken.rt.starter.storage.cons.StorageType;

/**
 * @author Ken
 * @className: ObsProperties
 * @createdTime: 2023/3/11 22:57
 * @desc:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@EnableConfigurationProperties(ObsProperties.class)
@ConfigurationProperties(prefix = StorageType.OBS)
public class ObsProperties extends StorageProperties {

}
