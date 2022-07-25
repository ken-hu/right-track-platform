package pers.ken.rt.gw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <name> GatewayApp </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 20:32.
 *
 * @author _Ken.Hu
 */
@SpringBootApplication(scanBasePackages = {"pers.ken.rt.gw", "pers.ken.rt.iam"})
@EnableDiscoveryClient
@EnableFeignClients("pers.ken.rt.iam")
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}
