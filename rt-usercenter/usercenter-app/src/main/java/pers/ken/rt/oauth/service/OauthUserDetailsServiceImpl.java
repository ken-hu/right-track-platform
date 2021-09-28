package pers.ken.rt.oauth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.ken.rt.oauth.entity.OauthUserDetail;
import pers.ken.rt.uc.entity.OauthUser;
import pers.ken.rt.uc.service.OauthUserService;

import java.util.Objects;

/**
 * <name> UserClientsServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:21.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class OauthUserDetailsServiceImpl implements UserDetailsService {

    private OauthUserService oauthUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OauthUser oauthUser = oauthUserService.getOauthUser(username);
        if (Objects.isNull(oauthUser)) {
            throw new UsernameNotFoundException("Can not found username: " + username);
        }
        return new OauthUserDetail(oauthUser);
    }
}
