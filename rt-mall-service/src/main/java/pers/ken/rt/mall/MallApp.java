package pers.ken.rt.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Name: MallApp
 * Creation Time: 2023/1/4 23:37.
 *
 * @author Ken
 */
@SpringBootApplication(scanBasePackages = {"pers.ken.rt.common.web", "pers.ken.rt.mall"})
@EnableDiscoveryClient
public class MallApp {
    public static void main(String[] args) {
        SpringApplication.run(MallApp.class, args);
    }

}
