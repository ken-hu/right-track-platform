package pers.ken.rt.iam.permission.access;

import org.springframework.util.StringUtils;
import pers.ken.rt.iam.internal.ResourceConvert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <code> ResourceConvertRegistry </code>
 * <desc> ResourceConvertRegistry </desc>
 * <b>Creation Time:</b> 2022/8/10 10:11.
 *
 * @author Ken.Hu
 */
public class ResourceConvertRegistry {
    private final ConcurrentHashMap<String, ResourceConvert<?>> convertMap = new ConcurrentHashMap<>();

    public void addConvert(ResourceConvert<?> convert) {
        if (!StringUtils.hasText(convert.resourceId())) {
            throw new IllegalArgumentException("Con not identify ResourceType");
        }
        this.convertMap.put(convert.resourceId(), convert);
    }

    protected ResourceConvert<?> getConvert(String resourceType) {
        return convertMap.get(resourceType);
    }
}
