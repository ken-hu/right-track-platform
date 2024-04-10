package pers.ken.rt.auth.application.service.impl;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pers.ken.rt.auth.domain.entity.aggregate.User;
import pers.ken.rt.auth.domain.repository.UserRepository;

/**
 * @author Ken
 * @className: UserDetailsServiceImpl
 * @createdTime: 2023/3/11 2:45
 * @desc:
 */
//@Service
//@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.get(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername().getName(),
                user.getPassword().getPassword(),
                user.isEnabled(),
                true,true,true, AuthorityUtils.NO_AUTHORITIES
                );
    }
}
