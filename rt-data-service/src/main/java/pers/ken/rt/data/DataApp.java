package pers.ken.rt.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Ken
 * @className: DataApp
 * @createdTime: 2023/3/14 14:40
 * @desc:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DataApp {
    public static void main(String[] args) {
        SpringApplication.run(DataApp.class, args);
    }
}
