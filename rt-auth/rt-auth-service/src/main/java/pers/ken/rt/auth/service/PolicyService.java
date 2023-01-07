package pers.ken.rt.auth.service;

import pers.ken.rt.auth.entity.Policy;

import java.util.List;

/**
 * <code> PolicyService </code>
 * <desc> PolicyService </desc>
 * <b>Creation Time:</b> 2021/12/3 13:50.
 *
 * @author _Ken.Hu
 */
public interface PolicyService {
    /**
     * User policies list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Policy> userPolicies(String userId);
}
