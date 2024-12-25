package pers.ken.rt.map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: ${NAME}
 * @Created: 2024/11/2
 * @Author ken
 */
@SpringBootApplication(
        scanBasePackages = {"pers.ken.rt.common.web", "pers.ken.rt.map"})
@MapperScan("pers.ken.rt.map.infrastructure.repository.persistence.mapper")
public class MapApp {
    public static void main(String[] args) {
        SpringApplication.run(MapApp.class, args);
    }
}