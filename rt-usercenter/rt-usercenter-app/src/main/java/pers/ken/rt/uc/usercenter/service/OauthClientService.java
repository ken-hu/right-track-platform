package pers.ken.rt.uc.usercenter.service;

import pers.ken.rt.uc.usercenter.entity.OauthClient;

/**
 * <name> ClientService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/27 23:39.
 *
 * @author _Ken.Hu
 */
public interface OauthClientService {
    OauthClient getClient(String clientId);
}
