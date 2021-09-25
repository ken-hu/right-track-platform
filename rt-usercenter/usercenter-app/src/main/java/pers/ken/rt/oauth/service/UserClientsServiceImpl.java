package pers.ken.rt.oauth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <name> UserClientsServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:21.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class UserClientsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
