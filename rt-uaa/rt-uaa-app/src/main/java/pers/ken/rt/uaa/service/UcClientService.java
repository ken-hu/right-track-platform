package pers.ken.rt.uaa.service;

import pers.ken.rt.uaa.entity.OauthClient;

/**
 * <name> ClientService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/27 23:39.
 *
 * @author _Ken.Hu
 */
public interface UcClientService {
    /**
     * Gets client.
     *
     * @param clientId the client id
     * @return the client
     */
    OauthClient getClient(String clientId);
}
