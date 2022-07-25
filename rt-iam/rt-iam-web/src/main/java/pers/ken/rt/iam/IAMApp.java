package pers.ken.rt.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <code> IAMApp </code>
 * <desc> IAMApp </desc>
 * <b>Creation Time:</b> 2022/7/19 17:12.
 *
 * @author Ken.Hu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IAMApp {
    public static void main(String[] args) {
        SpringApplication.run(IAMApp.class, args);
    }
}
