package pers.ken.rt.gw.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <code> Oauth2Properties </code>
 * <desc> Oauth2Properties </desc>
 * <b>Creation Time:</b> 2021/10/17 1:59.
 *
 * @author _Ken.Hu
 */
@Data
@Configuration
@ConfigurationProperties("oauth2")
public class Oauth2Properties {
    private  List<String> whiteListUrls;
}
