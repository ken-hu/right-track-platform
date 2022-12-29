//package pers.ken.rt.auth.service.impl;
//
//import lombok.AllArgsConstructor;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.ClientRegistrationException;
//import org.springframework.security.oauth2.provider.NoSuchClientException;
//import org.springframework.security.oauth2.provider.client.BaseClientDetails;
//import org.springframework.stereotype.Service;
//import pers.ken.rt.auth.entity.Oauth2Client;
//import pers.ken.rt.auth.service.UcClientService;
//
///**
// * <name> ClientServiceImpl </name>
// * <desc> </desc>
// * Creation Time: 2021/9/20 22:13.
// *
// * @author _Ken.Hu
// */
//@Service
//@AllArgsConstructor
//public class OauthClientDetailsServiceImpl implements ClientDetailsService {
//
//    private final UcClientService ucClientService;
//
//    @Override
//    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//        Oauth2Client oauth2Client = ucClientService.getClient(clientId);
//        if (null == oauth2Client) {
//            throw new NoSuchClientException("Cannot found client:" + clientId);
//        }
//        BaseClientDetails baseClientDetails = new BaseClientDetails(
//                oauth2Client.getClientId(),
//                oauth2Client.getResourceIds(),
//                oauth2Client.getScope(),
//                oauth2Client.getAuthorizedGrantTypes(),
//                oauth2Client.getAuthorities(),
//                oauth2Client.getRegisteredRedirectUri()
//        );
//        baseClientDetails.setAccessTokenValiditySeconds(oauth2Client.getAccessTokenValiditySeconds());
//        baseClientDetails.setRefreshTokenValiditySeconds(oauth2Client.getRefreshTokenValiditySeconds());
//
//        return baseClientDetails;
//    }
//}
