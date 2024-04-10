package pers.ken.rt.auth.domain.repository;

import pers.ken.rt.auth.domain.entity.aggregate.Policy;

import java.util.List;

/**
 * <code> PolicyService </code>
 * <desc> PolicyService </desc>
 * <b>Creation Time:</b> 2021/12/3 13:50.
 *
 * @author _Ken.Hu
 */
public interface PolicyRepository {

    /**
     * Create policy.
     *
     * @param policy the policy
     */
    void savePolicy(Policy policy);


    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * List policies list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Policy> findListByUserId(Long userId);

    /**
     * List policies list.
     *
     * @param roleIds the role ids
     * @return the list
     */
    List<Policy> findListByRoleIds(List<Long> roleIds);


    /**
     * List policies by group id list.
     *
     * @param groupIds the group ids
     * @return the list
     */
    List<Policy> findListByGroupIds(List<Long> groupIds);
}
