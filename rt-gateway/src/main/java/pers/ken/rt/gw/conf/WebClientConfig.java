package pers.ken.rt.gw.conf;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <code> WebClientConfig </code>
 * <desc> WebClientConfig </desc>
 * <b>Creation Time:</b> 2022/7/28 15:23.
 *
 * @author Ken.Hu
 */
@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
