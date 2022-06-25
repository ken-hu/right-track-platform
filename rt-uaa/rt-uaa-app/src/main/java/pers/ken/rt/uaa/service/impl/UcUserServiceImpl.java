package pers.ken.rt.uaa.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.uaa.entity.OauthUser;
import pers.ken.rt.uaa.repository.UcUserRepository;
import pers.ken.rt.uaa.service.UcUserService;

/**
 * <name> OauthUserServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:44.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class UcUserServiceImpl implements UcUserService {
    private final UcUserRepository ucUserRepository;

    @Override
    public OauthUser getOauthUser(String username) {
        return ucUserRepository.findByUsername(username);
    }
}
