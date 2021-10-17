package pers.ken.rt.uc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * <name> SwaggerConfig </name>
 * <desc> SwaggerConfig </desc>
 * Creation Time: 2021/10/7 15:01.
 *
 * @author _Ken.Hu
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()).enable(true)
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.basePackage("pers.ken.rt.uc.*"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("RT UserCenter Api Docs")
                .description("RT UserCenter Api Docs")
                .contact(new Contact("Swagger", "URL", "Email"))
                .version("2.0")
                .build();
    }
}
