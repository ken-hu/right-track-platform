package pers.ken.rt.uc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * <name> UserCenterApp </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 12:29.
 *
 * @author _Ken.Hu
 */
@EnableOpenApi
@SpringBootApplication
@EnableDiscoveryClient
public class UserCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApp.class, args);
    }
}