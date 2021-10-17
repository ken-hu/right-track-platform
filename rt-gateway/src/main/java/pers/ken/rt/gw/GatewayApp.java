package pers.ken.rt.gw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * <name> GatewayApp </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 20:32.
 *
 * @author _Ken.Hu
 */
@SpringBootApplication
@EnableOpenApi
@EnableDiscoveryClient
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}
