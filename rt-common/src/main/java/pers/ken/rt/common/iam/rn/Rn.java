package pers.ken.rt.common.iam.rn;

import lombok.Builder;
import lombok.Data;

/**
 * <code> Krn </code>
 * <pre>
 * Support resources
 * <p>
 * krn:<service>:<account>:<resourcetype>:resource:qualifier
 * <p/>
 * <pre/>
 * <b>Creation Time:</b> 2022/2/12 0:48.
 * @author _Ken.Hu
 */
@Data
@Builder
public class Rn {
    private String service;
    private String resourceType;
    private String resource;
    private RnResource rnResource;

    private final static String KRN = "krn";

    public static Rn fromString(String crn) {
        int krnColonIdx = crn.indexOf(':');
        if (krnColonIdx < 0 || !KRN.equals(crn.substring(0, krnColonIdx))) {
            throw new IllegalArgumentException("Malformed Namespace - doesn't start with 'krn:'");
        }

        int serviceColonIdx = crn.indexOf(':', krnColonIdx + 1);
        if (serviceColonIdx < 0) {
            throw new IllegalArgumentException("Malformed Krn - no service specified");
        }
        String service = crn.substring(krnColonIdx + 1, serviceColonIdx);

        int resourceTypeColonIndex = crn.indexOf(':', serviceColonIdx + 1);
        String resourceType = crn.substring(serviceColonIdx + 1, resourceTypeColonIndex);
        if (resourceType.isEmpty()) {
            throw new IllegalArgumentException("Malformed Krn - no resourceType specified");
        }

        String resource = crn.substring(serviceColonIdx + 1);
        if (resource.isEmpty()) {
            throw new IllegalArgumentException("Malformed Krn - no resource specified");
        }

        return Rn.builder()
                .service(service)
                .resourceType(resourceType)
                .resource(resource)
                .rnResource(RnResource.fromString(resource))
                .build();
    }
}
