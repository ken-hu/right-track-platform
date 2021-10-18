package pers.ken.rt.uc.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import pers.ken.rt.uc.entity.OauthClient;
import pers.ken.rt.uc.service.UcClientService;

/**
 * <name> ClientServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:13.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class OauthClientDetailsServiceImpl implements ClientDetailsService {

    private final UcClientService ucClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClient oauthClient = ucClientService.getClient(clientId);
        if (null == oauthClient) {
            throw new NoSuchClientException("Cannot found client:" + clientId);
        }
        BaseClientDetails baseClientDetails = new BaseClientDetails(
                oauthClient.getClientId(),
                oauthClient.getResourceIds(),
                oauthClient.getScope(),
                oauthClient.getAuthorizedGrantTypes(),
                oauthClient.getAuthorities(),
                oauthClient.getRegisteredRedirectUri()
        );
        baseClientDetails.setAccessTokenValiditySeconds(oauthClient.getAccessTokenValiditySeconds());
        baseClientDetails.setRefreshTokenValiditySeconds(oauthClient.getRefreshTokenValiditySeconds());

        return baseClientDetails;
    }
}
