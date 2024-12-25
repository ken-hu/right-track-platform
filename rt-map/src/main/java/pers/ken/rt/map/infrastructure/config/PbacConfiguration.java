package pers.ken.rt.map.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pers.ken.rt.starter.pbac.annotation.PbacAccessControlEnable;
import pers.ken.rt.starter.pbac.core.DynamicExpressionsProvider;

/**
 * @ClassName: PbacConfiguration
 * @Created: 2024/11/4 12:12
 * @Author ken
 */
@Configuration
@Slf4j
@PbacAccessControlEnable
public class PbacConfiguration {
    @Bean
    @Primary
    public DynamicExpressionsProvider dynamicExpressionProvider() {
        return new ExpressionsParseProvider();
    }
}
