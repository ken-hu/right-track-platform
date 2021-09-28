package pers.ken.rt.uc.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.uc.entity.OauthClient;
import pers.ken.rt.uc.repository.OauthClientRepository;
import pers.ken.rt.uc.service.OauthClientService;

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
