package pers.ken.rt.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <name> UserCenterApp </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 12:29.
 *
 * @author _Ken.Hu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
