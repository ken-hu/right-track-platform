package pers.ken.rt.uaa.iam;

import org.casbin.annotation.CasbinDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <code> CasbinDataSource </code>
 * <desc> CasbinDataSource </desc>
 * <b>Creation Time:</b> 2022/6/14 15:59.
 *
 * @author Ken.Hu
 */
@Configuration
public class CasbinDataSourceConfiguration {
    @Bean
    @CasbinDataSource
    public DataSource casbinDataSource() {
        return DataSourceBuilder.create().url("jdbc:h2:mem:casbin").build();
    }
}
