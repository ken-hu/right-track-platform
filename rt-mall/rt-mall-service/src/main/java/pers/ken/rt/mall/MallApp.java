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
@SpringBootApplication
@EnableDiscoveryClient
public class MallApp {
    public static void main(String[] args) {
        SpringApplication.run(MallApp.class, args);
    }
}
