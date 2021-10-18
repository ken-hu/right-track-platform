package pers.ken.rt.uc.service;

import pers.ken.rt.uc.entity.OauthClient;

/**
 * <name> ClientService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/27 23:39.
 *
 * @author _Ken.Hu
 */
public interface UcClientService {
    OauthClient getClient(String clientId);
}
