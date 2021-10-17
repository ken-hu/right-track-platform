package pers.ken.rt.uc.usercenter.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.uc.usercenter.entity.OauthClient;
import pers.ken.rt.uc.usercenter.repository.OauthClientRepository;
import pers.ken.rt.uc.usercenter.service.OauthClientService;

/**
 * <name> ClientServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/28 0:02.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class OauthClientServiceImpl implements OauthClientService {
    private final OauthClientRepository oauthClientRepository;

    @Override
    public OauthClient getClient(String clientId) {
        return oauthClientRepository.findByClientId(clientId);
    }
}
