package pers.ken.rt.starter.storage.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pers.ken.rt.starter.storage.cons.StorageType;
import pers.ken.rt.starter.storage.StorageProperties;

/**
 * @author Ken
 * @className: OssProperties
 * @createdTime: 2023/3/11 22:49
 * @desc:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@EnableConfigurationProperties(OssProperties.class)
@ConfigurationProperties(prefix = StorageType.OSS)
public class OssProperties extends StorageProperties {
}
