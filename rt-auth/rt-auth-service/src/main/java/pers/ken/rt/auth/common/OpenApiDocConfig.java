package pers.ken.rt.auth.common;

import io.swagger.v3.oas.models.media.*;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <code> SwaggerConfig </code>
 * <desc> SwaggerConfig </desc>
 * <b>Creation Time:</b> 2022/5/15 19:24.
 *
 * @author Ken.Hu
 */
@Configuration
public class OpenApiDocConfig {

    @Bean
    public OpenApiCustomiser customizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
            final Content content = operation.getResponses().get("200").getContent();
            if (null != content) {
                content.keySet().forEach(mediaTypeKey -> {
                    final MediaType mediaType = content.get(mediaTypeKey);
                    mediaType.schema(this.customizeSchema(mediaType.getSchema()));
                });
            }else {
                Content newContent = new Content();
                MediaType mediaType = new MediaType();
                MediaType schema = mediaType.schema(this.customizeSchema(mediaType.getSchema()));
                newContent.addMediaType("*/*",schema);
                operation.getResponses().get("200").setContent(newContent);
            }
        }));
    }

    private Schema<?> customizeSchema(final Schema<?> objSchema) {
        final Schema<?> wrapperSchema = new Schema<>();
        wrapperSchema.addProperty("succeed", new BooleanSchema()._default(true));
        wrapperSchema.addProperty("code", new IntegerSchema()._default(0));
        wrapperSchema.addProperty("message", new StringSchema()._default("Success"));
        wrapperSchema.addProperty("requestId", new StringSchema()._default("requestId"));
        wrapperSchema.addProperty("data", objSchema);
        return wrapperSchema;
    }
}
