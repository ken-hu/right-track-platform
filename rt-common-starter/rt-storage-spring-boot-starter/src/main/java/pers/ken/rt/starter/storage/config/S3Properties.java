package pers.ken.rt.starter.storage.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pers.ken.rt.starter.storage.StorageProperties;
import pers.ken.rt.starter.storage.cons.StorageType;

/**
 * @author Ken
 * @className: S3Properties
 * @createdTime: 2023/3/11 22:57
 * @desc:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@EnableConfigurationProperties(S3Properties.class)
@ConfigurationProperties(prefix = StorageType.S3)
public class S3Properties extends StorageProperties {
}
