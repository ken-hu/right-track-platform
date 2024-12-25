package pers.ken.rt.auth.oauth.support;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.oauth.model.AuthUserDetails;
import pers.ken.rt.auth.repository.mapper.RoleMapper;
import pers.ken.rt.auth.repository.mapper.TenantMapper;
import pers.ken.rt.auth.repository.po.Account;
import pers.ken.rt.auth.repository.po.Role;
import pers.ken.rt.auth.repository.po.Tenant;
import pers.ken.rt.auth.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: AuthUserDetailService
 * @Created: 2024/11/20 20:07
 * @Author ken
 */
@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {
    private final AccountService accountService;
    private final TenantMapper tenantMapper;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getByUsername(username);
        if (null == account) {
            throw new UsernameNotFoundException("Account not found");
        }
        Tenant tenant = tenantMapper.selectOne(
                Wrappers.lambdaQuery(Tenant.class)
                        .eq(Tenant::getTenantCode, account.getTenantCode())
        );
        List<Role> roles = roleMapper.selectByUser(account.getId());
        List<String> roleNames = roles.stream().map(Role::getName).toList();
        // todo authoritiesStr ??
        ArrayList<String> authoritiesStr = Lists.newArrayList("user", "admin", "app", "web");
        List<SimpleGrantedAuthority> authorities = authoritiesStr.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new AuthUserDetails(account.getId(), account.getUsername(), account.getPassword(), account.getStatus(), account.getTenantCode(), tenant.getId(), roleNames, authorities);
    }
}
