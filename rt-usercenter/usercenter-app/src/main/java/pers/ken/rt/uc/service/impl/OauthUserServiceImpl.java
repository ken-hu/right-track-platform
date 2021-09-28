package pers.ken.rt.uc.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.uc.entity.OauthUser;
import pers.ken.rt.uc.repository.OauthUserRepository;
import pers.ken.rt.uc.service.OauthUserService;

/**
 * <name> OauthUserServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:44.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class OauthUserServiceImpl implements OauthUserService {
    private final OauthUserRepository oauthUserRepository;

    @Override
    public OauthUser getOauthUser(String username) {
        return oauthUserRepository.findByUsername(username);
    }
}
