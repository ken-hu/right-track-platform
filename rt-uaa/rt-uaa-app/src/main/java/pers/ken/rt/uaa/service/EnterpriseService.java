package pers.ken.rt.uaa.service;

import pers.ken.rt.uaa.dto.resp.EnterpriseListResp;

import java.util.List;

/**
 * <code> EnterpriseService </code>
 * <desc> EnterpriseService </desc>
 * <b>Creation Time:</b> 2022/2/12 1:26.
 *
 * @author _Ken.Hu
 */
public interface EnterpriseService {
    List<EnterpriseListResp> listAll();
}
