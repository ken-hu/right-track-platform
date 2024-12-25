package pers.ken.rt.starter.pbac.core;

import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.List;

/**
 * <code> PolicyGetHandler </code>
 * <desc> PolicyGetHandler </desc>
 * <b>Creation Time:</b> 2022/8/24 10:05.
 *
 * @author Ken.Hu
 */
public interface PolicyProvider {
    /**
     * User policies list.
     *
     * @return the list
     */
    List<PolicyDocument> loadMyPolicies();
}
