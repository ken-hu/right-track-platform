package pers.ken.rt.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <code> TestApp </code>
 * <desc> TestApp </desc>
 * <b>Creation Time:</b> 2022/7/6 14:19.
 *
 * @author Ken.Hu
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

    @GetMapping("/desc")
    public String getDesc(String desc) {
        return desc + "1";
    }

}
