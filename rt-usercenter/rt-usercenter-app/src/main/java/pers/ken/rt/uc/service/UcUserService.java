package pers.ken.rt.uc.service;

import pers.ken.rt.uc.entity.OauthUser;

/**
 * <name> OauthUserService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:43.
 *
 * @author _Ken.Hu
 */
public interface UcUserService {
    OauthUser getOauthUser(String username);
}
