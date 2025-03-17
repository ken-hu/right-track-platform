package pers.ken.rt.gw.config;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code> OpenApiDocGroupConfig </code>
 * <desc> OpenApi3.0 Group by for gateway config </desc>
 * <b>Creation Time:</b> 2022/5/15 19:24.
 *
 * @author Ken.Hu
 */
@Slf4j
@Configuration
public class OpenApiDocGroupConfig {

    @Bean
    @Lazy
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteDefinitionLocator locator) {
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        if (CollectionUtils.isEmpty(definitions)) {
            return new ArrayList<>();
        }
        for (RouteDefinition definition : definitions) {
            log.info("Route service id: {} | {}", definition.getId(), definition.getUri().toString());
        }
        return definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .map(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    swaggerUiConfigParameters.addGroup(name);
                    return GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
                }).collect(Collectors.toList());
    }
}
