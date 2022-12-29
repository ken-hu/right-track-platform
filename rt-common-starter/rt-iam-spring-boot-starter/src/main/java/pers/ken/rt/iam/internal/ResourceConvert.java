package pers.ken.rt.iam.internal;

import pers.ken.rt.iam.permission.data.IDataProvider;

/**
 * <code> ResourceConvert </code>
 * <desc> ResourceConvert </desc>
 * <b>Creation Time:</b> 2022/8/8 16:29.
 *
 * @param <T> the type parameter
 * @author Ken.Hu
 */
public interface ResourceConvert<T extends AbstractResource> extends IDataProvider {
    /**
     * Convert t.
     *
     * @param resource the resource
     * @return the t
     */
    T convert(Rn resource);


    /**
     * Resource type string.
     *
     * @return the string
     */
    String resourceId();
}
