package pers.ken.rt.auth.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: SpringDocumentConfiguration
 * @Created: 2025/3/1 18:06
 * @Author ken
 */
@Configuration
@Slf4j
public class SpringDocumentConfiguration {
    @Bean
    public OpenAPI openApi() {
        log.info("OpenAPI Spring document init");
        return new OpenAPI()
            .info(new Info().title("RightTrack-OpenAPI")
                .description("Springboot3.x")
                .version("v0.0.1"))
            .externalDocs(new ExternalDocumentation()
                .description("Build by SpringDocument")
                .url("https://springshop.wiki.github.org/docs"));
    }
}
