package pers.ken.rt.uc.oauth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import pers.ken.rt.uc.usercenter.entity.OauthClient;
import pers.ken.rt.uc.usercenter.service.OauthClientService;

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

    private final OauthClientService oauthClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClient oauthClient = oauthClientService.getClient(clientId);
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
