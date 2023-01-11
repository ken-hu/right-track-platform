package pers.ken.rt.gw.conf;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.common.model.PlatformResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * <code> OpenApiDocGroupConfig </code>
 * <desc> OpenApi3.0 Group by for gateway config </desc>
 * <b>Creation Time:</b> 2022/5/15 19:24.
 *
 * @author Ken.Hu
 */
@Configuration
public class OpenApiDocGroupConfig {

    @Bean
    @Lazy(value = false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteDefinitionLocator locator) {
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        if (CollectionUtils.isEmpty(definitions)) {
            return new ArrayList<>();
        }
        for (RouteDefinition definition : definitions) {
            System.out.println("Route service id: " + definition.getId() + "  " + definition.getUri().toString());
        }
        return definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .map(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    swaggerUiConfigParameters.addGroup(name);
                    return GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
                }).collect(Collectors.toList());
    }

    @Bean
    public OpenApiCustomiser customOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

                Schema sharedErrorSchema = ModelConverters.getInstance()
                        .read(PlatformResult.class)
                        .getOrDefault("Error", new Schema());

                MediaType sharedMediaType = new MediaType().schema(sharedErrorSchema);
                Content sharedContent = new Content()
                        .addMediaType(APPLICATION_JSON_VALUE, sharedMediaType);

                ApiResponses apiResponses = operation.getResponses();

                ApiResponse response = new ApiResponse()
                        .description("Unhandled server error")
                        .content(sharedContent);
                apiResponses.addApiResponse("500", response);
                apiResponses.addApiResponse("200", response);
            }));
        };
    }
}
