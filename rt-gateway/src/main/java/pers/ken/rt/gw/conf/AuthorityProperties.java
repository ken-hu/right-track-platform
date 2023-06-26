package pers.ken.rt.gw.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Ken
 * @className: AuthorityProperties
 * @createdTime: 2023/4/18 14:09
 * @desc:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth.resource-server")
public class AuthorityProperties {
    private List<String> whiteList;
}
