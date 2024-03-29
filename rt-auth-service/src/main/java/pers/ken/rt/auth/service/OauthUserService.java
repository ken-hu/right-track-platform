package pers.ken.rt.auth.service;

import pers.ken.rt.auth.entity.OauthUser;

/**
 * <name> OauthUserService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:43.
 *
 * @author _Ken.Hu
 */
public interface OauthUserService {
    /**
     * Gets oauth user.
     *
     * @param username the username
     * @return the oauth user
     */
    OauthUser getOauthUser(String username);

    /**
     * Gets oauth user.
     *
     * @param id the id
     * @return the oauth user
     */
    OauthUser getOauthUser(Long id);
}
