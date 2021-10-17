package pers.ken.rt.od;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <code> OrderApp </code>
 * <desc> OrderApp </desc>
 * <b>Creation Time:</b> 2021/10/16 22:49.
 *
 * @author _Ken.Hu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }
}
