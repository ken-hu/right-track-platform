package pers.ken.rt.common.iam;

import pers.ken.rt.common.iam.rn.ResourceId;
import pers.ken.rt.common.iam.rn.Rn;
import pers.ken.rt.common.iam.rn.RnConverter;

import java.util.List;

/**
 * <code> PolicyResolve </code>
 * <desc> PolicyResolve </desc>
 * <b>Creation Time:</b> 2022/6/14 17:09.
 *
 * @author Ken.Hu
 */
public class PolicyResolve {

    /**
     * Resource resolve list.
     *
     * @param <T>             the type parameter
     * @param resourceMatcher the resource matcher
     *                        example: mars:city:adcode/*
     * @param resource        the resource
     * @return the list
     */
    public static <T extends ResourceId> T resourceResolve(List<String> resourceMatcher, Class<T> resource) {
        resourceMatcher.stream().map(r -> {
            Rn rn = Rn.fromString(r);
            String service = rn.getService();
        });
    }
}
