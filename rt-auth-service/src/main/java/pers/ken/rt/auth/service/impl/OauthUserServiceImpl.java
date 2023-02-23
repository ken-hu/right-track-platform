package pers.ken.rt.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.auth.entity.OauthUser;
import pers.ken.rt.auth.exception.UserException;
import pers.ken.rt.auth.repository.OauthUserRepository;
import pers.ken.rt.auth.service.OauthUserService;
import pers.ken.rt.common.exception.ServiceCode;

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

    @Override
    public OauthUser getOauthUser(Long id) {
        return oauthUserRepository.findById(id).orElseThrow(() -> new UserException(ServiceCode.MISSING_DATA));
    }
}
