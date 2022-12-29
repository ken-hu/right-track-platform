package pers.ken.rt.auth.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pers.ken.rt.auth.entity.OauthUser;
import pers.ken.rt.auth.oauth.model.OauthUserDetail;
import pers.ken.rt.auth.service.UcUserService;

import java.util.Objects;

/**
 * <name> UserClientsServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:21.
 *
 * @author _Ken.Hu
 */
//@Service
//@AllArgsConstructor
public class OauthUserDetailsServiceImpl implements UserDetailsService {

    private UcUserService ucUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OauthUser oauthUser = ucUserService.getOauthUser(username);
        if (Objects.isNull(oauthUser)) {
            throw new UsernameNotFoundException("Can not found username: " + username);
        }
        return new OauthUserDetail(oauthUser);
    }
}
