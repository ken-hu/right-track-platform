package pers.ken.rt.uaa.service;

import pers.ken.rt.uaa.entity.OauthUser;

/**
 * <name> OauthUserService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:43.
 *
 * @author _Ken.Hu
 */
public interface UcUserService {
    /**
     * Gets oauth user.
     *
     * @param username the username
     * @return the oauth user
     */
    OauthUser getOauthUser(String username);
}
