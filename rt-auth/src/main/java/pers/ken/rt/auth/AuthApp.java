package pers.ken.rt.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <name> UserCenterApp </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 12:29.
 *
 * @author _Ken.Hu
 */
@SpringBootApplication(scanBasePackages = {"pers.ken.rt.common.web","pers.ken.rt.auth"})
@MapperScan("pers.ken.rt.auth.repository.mapper")
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
