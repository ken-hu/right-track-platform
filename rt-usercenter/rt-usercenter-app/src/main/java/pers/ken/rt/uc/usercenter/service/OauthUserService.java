package pers.ken.rt.uc.usercenter.service;

import pers.ken.rt.uc.usercenter.entity.OauthUser;

/**
 * <name> OauthUserService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:43.
 *
 * @author _Ken.Hu
 */
public interface OauthUserService {
    OauthUser getOauthUser(String username);
}
