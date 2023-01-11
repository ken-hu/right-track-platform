package pers.ken.rt.auth.service;

import pers.ken.rt.auth.dto.req.PolicyCreateReq;

/**
 * <code> PolicyService </code>
 * <desc> PolicyService </desc>
 * <b>Creation Time:</b> 2021/12/3 13:50.
 *
 * @author _Ken.Hu
 */
public interface PolicyService {

    /**
     * Create policy.
     *
     * @param req the req
     */
    void createPolicy(PolicyCreateReq req);
}
