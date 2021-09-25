package pers.ken.rt.oauth.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * <name> ClientServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:13.
 *
 * @author _Ken.Hu
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return null;
    }
}
