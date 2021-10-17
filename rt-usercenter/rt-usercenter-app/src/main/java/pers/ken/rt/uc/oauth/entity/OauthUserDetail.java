package pers.ken.rt.uc.oauth.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.ken.rt.uc.usercenter.entity.OauthUser;

import java.util.Collection;

/**
 * <name> OauthUserDetail </name>
 * <desc> </desc>
 * Creation Time: 2021/9/28 0:55.
 *
 * @author _Ken.Hu
 */
@Data
public class OauthUserDetail implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private String clientId;


    private Collection<SimpleGrantedAuthority> authorities;

    public OauthUserDetail(OauthUser oauthUser) {
        this.id = oauthUser.getId();
        this.username = oauthUser.getUsername();
        this.password = oauthUser.getPassword();
        this.enabled = oauthUser.getStatus().equals(OauthUser.OauthUserStatus.ENABLE);
        this.authorities = Lists.newArrayList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
