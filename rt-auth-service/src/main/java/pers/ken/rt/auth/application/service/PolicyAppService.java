package pers.ken.rt.auth.application.service;

import pers.ken.rt.auth.interfaces.dto.req.PolicySaveRequest;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyItemResp;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyResp;

import java.util.List;

/**
 * The interface Policy app service.
 *
 * @author Ken
 * @className: PolicyAppService
 * @createdTime: 2023 /3/9 14:04
 * @desc:
 */
public interface PolicyAppService {
    /**
     * User policies list.
     *
     * @param userId the user id
     * @return the list
     */
    List<PolicyResp> userPolicies(Long userId);

    /**
     * User policy items list.
     *
     * @param userId the user id
     * @return the list
     */
    List<PolicyItemResp> userPolicyItems(Long userId);

    /**
     * Create.
     *
     * @param saveRequest the save request
     */
    void create(PolicySaveRequest saveRequest);

    /**
     * Update.
     *
     * @param id          the id
     * @param saveRequest the save request
     */
    void update(Long id, PolicySaveRequest saveRequest);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);
}
