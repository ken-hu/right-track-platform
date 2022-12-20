package pers.ken.rt.iam.internal;

import lombok.Builder;
import lombok.Data;

/**
 * <code> Crn </code>
 * <desc> Channel resource name </desc>
 * <b>Creation Time:</b> 2022/1/15 1:05.
 *
 * @author _Ken.Hu
 */
@Builder
@Data
public class Rn {
    private String service;
    private String resource;
    private RnResource rnResource;

    public static Rn fromString(String rn) {
        int channelColonIndex = rn.indexOf(':');
        if (channelColonIndex < 0 || !"channel".equals(rn.substring(0, channelColonIndex))) {
            throw new IllegalArgumentException("Malformed Namespace - doesn't start with 'channel:'");
        }

        int serviceColonIndex = rn.indexOf(':', channelColonIndex + 1);
        if (serviceColonIndex < 0) {
            throw new IllegalArgumentException("Malformed RN - no service specified");
        }
        String service = rn.substring(channelColonIndex + 1, serviceColonIndex);
//
//        int resourceTypeColonIndex = rn.indexOf(':', serviceColonIndex + 1);
//        String resourceType = rn.substring(serviceColonIndex + 1, resourceTypeColonIndex);
//        if (resourceType.isEmpty()) {
//            throw new IllegalArgumentException("Malformed RN - no resourceType specified");
//        }

        String resource = rn.substring(serviceColonIndex+1);
        if (resource.isEmpty()) {
            throw new IllegalArgumentException("Malformed RN - no resource specified");
        }

        return Rn.builder()
                .service(service)
                .resource(resource)
                .rnResource(RnResource.fromString(resource))
                .build();
    }
}
