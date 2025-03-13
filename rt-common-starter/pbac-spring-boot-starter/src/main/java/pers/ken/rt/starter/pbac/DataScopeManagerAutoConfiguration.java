package pers.ken.rt.starter.pbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ken.rt.starter.pbac.permission.data.DataPermissionProvider;
import pers.ken.rt.starter.pbac.permission.data.DataScopeAspect;

/**
 * @ClassName: DataScopeManagerAutoConfiguration
 * @Created: 2025/3/12 18:14
 * @Author ken
 */
@Configuration
@EnableConfigurationProperties(PbacProperties.class)
@ConditionalOnProperty(value = PbacProperties.ACCESS_CONTROL_DATA_ENABLE, havingValue = "true")
@Slf4j
public class DataScopeManagerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DataScopeAspect dataScopeAspect(DataPermissionProvider dataPermissionProvider) {
        log.info("DataScopeManagerAutoConfiguration dataScopeAspect init succeed...");
        return new DataScopeAspect(dataPermissionProvider);
    }
}
