package pers.ken.rt.iam.config;

import org.casbin.annotation.CasbinDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <code> CasbinDataSourceConfiguration </code>
 * <desc> CasbinDataSourceConfiguration </desc>
 * <b>Creation Time:</b> 2022/7/19 17:11.
 *
 * @author Ken.Hu
 */
@Configuration
public class CasbinDataSourceConfiguration {
    @Bean
    @CasbinDataSource
    public DataSource casbinDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/postgres?useUnicode=true&characterEncoding=utf-8")
                .username("postgres")
                .password("IWHb4wDvb8xjCfBy")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
