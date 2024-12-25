package pers.ken.rt.auth.oauth.support.password;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import pers.ken.rt.auth.oauth.model.AuthUserDetails;

import java.util.Collection;

/**
 * @ClassName: PasswordAuthenticationToken
 * @Created: 2024/12/11 16:24
 * @Author ken
 */
@Setter
@Getter
public class PasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private AuthUserDetails authUserDetails;

    public PasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
