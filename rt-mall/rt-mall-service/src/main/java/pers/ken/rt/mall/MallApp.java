package pers.ken.rt.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Name: MallApp
 * Creation Time: 2023/1/4 23:37.
 *
 * @author Ken
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MallApp {
    public static void main(String[] args) {
        SpringApplication.run(MallApp.class, args);
    }

    @GetMapping(value = "/test")
    public String test(HttpServletRequest request) {
        return request.getHeader("X-User-Info");
    }
}
