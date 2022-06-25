package pers.ken.rt.common.iam.rn;

import lombok.Builder;
import lombok.Getter;

/**
 * <code> KrnResource </code>
 * <desc> KrnResource </desc>
 * <b>Creation Time:</b> 2022/2/12 0:48.
 *
 * @author _Ken.Hu
 */
@Builder
@Getter
public class RnResource {
    private final String resourceType;
    private final String resource;
    private final String qualifier;

    public static RnResource fromString(String resource) {
        Integer resourceTypeBoundary = null;
        Integer resourceIdBoundary = null;

        for (int i = 0; i < resource.length(); ++i) {
            char ch = resource.charAt(i);

            if (ch == ':' || ch == '/') {
                resourceTypeBoundary = i;
                break;
            }
        }

        if (resourceTypeBoundary != null) {
            for (int i = resource.length() - 1; i > resourceTypeBoundary; --i) {
                char ch = resource.charAt(i);

                if (ch == ':') {
                    resourceIdBoundary = i;
                    break;
                }
            }
        }

        if (resourceTypeBoundary == null) {
            // 'resource-id'
            return RnResource.builder().resource(resource).build();
        } else if (resourceIdBoundary == null) {
            // 'resource-type:resource-id'
            String resourceType = resource.substring(0, resourceTypeBoundary);
            String resourceId = resource.substring(resourceTypeBoundary + 1);
            return RnResource.builder().resourceType(resourceType).resource(resourceId).build();
        } else {
            // 'resource-type:resource-id:qualifier'
            String resourceType = resource.substring(0, resourceTypeBoundary);
            String resourceId = resource.substring(resourceTypeBoundary + 1, resourceIdBoundary);
            String qualifier = resource.substring(resourceIdBoundary + 1);
            return RnResource.builder()
                    .resourceType(resourceType)
                    .resource(resourceId)
                    .qualifier(qualifier)
                    .build();
        }
    }
}
