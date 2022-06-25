package pers.ken.rt.common.iam.rn;

/**
 * <code> ResourceId </code>
 * <desc> ResourceId </desc>
 * <b>Creation Time:</b> 2022/6/15 10:51.
 *
 * @author Ken.Hu
 */
public interface ResourceId {
    /**
     * Gets resource id.
     *
     * @return the resource id
     */
    default String getResourceId() {
        return "default";
    }
}
