package pers.ken.rt.starter.pbac.internal;

import java.util.List;

/**
 * <code> PolicyGetHandler </code>
 * <desc> PolicyGetHandler </desc>
 * <b>Creation Time:</b> 2022/8/24 10:05.
 *
 * @author Ken.Hu
 */
public interface PolicyGetHandler {
    /**
     * User policies list.
     *
     * @return the list
     */
    List<Policy> loadPolicies();
}
