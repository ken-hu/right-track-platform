package pers.ken.rt.uaa.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.uaa.entity.OauthClient;
import pers.ken.rt.uaa.repository.UcClientRepository;
import pers.ken.rt.uaa.service.UcClientService;

/**
 * <name> ClientServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/28 0:02.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class UcClientServiceImpl implements UcClientService {
    private final UcClientRepository ucClientRepository;

    @Override
    public OauthClient getClient(String clientId) {
        return ucClientRepository.findByClientId(clientId);
    }
}
