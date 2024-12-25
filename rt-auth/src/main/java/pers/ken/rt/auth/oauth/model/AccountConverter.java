package pers.ken.rt.auth.oauth.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pers.ken.rt.auth.repository.po.Account;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: SecurityUserConvert
 * @Created: 2024/12/5 16:37
 * @Author ken
 */
public interface AccountConverter {

    static User convert(Account account, List<String> authoritiesStr) {
        boolean enabled = account.getStatus().equalsIgnoreCase("enabled");
        List<SimpleGrantedAuthority> authorities = authoritiesStr.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(account.getUsername(),
                account.getPassword(),
                enabled,
                true,
                true,
                true,
                authorities);
    }
}
