package pers.ken.rt.iam;

import pers.ken.rt.iam.permission.access.ResourceConvertRegistry;

/**
 * <code> AccessManagementSupport </code>
 * <desc> AccessManagementSupport </desc>
 * <b>Creation Time:</b> 2022/8/10 10:38.
 *
 * @author Ken.Hu
 */
public interface AccessManagementSupportConfiguration {

    /**
     * Add Resource Convert.
     *
     * @param registry the registry
     */
    default void addResourceConvert(ResourceConvertRegistry registry) {

    }

}
