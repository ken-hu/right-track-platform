package pers.ken.rt.auth.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName: AuthUserDetails
 * @Created: 2024/12/10 14:19
 * @Author ken
 */
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class AuthUserDetails implements UserDetails {
    @Getter
    private Integer userId;
    private String username;
    private String password;
    @Getter
    private String status;
    private Collection<? extends GrantedAuthority> authorities;
    @Getter
    private String tenantCode;
    @Getter
    private Integer tenantId;
    @Getter
    private List<String> roles;

    public AuthUserDetails(Integer userId, String username, String password, String status, String tenantCode, Integer tenantId, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.tenantCode = tenantCode;
        this.tenantId = tenantId;
        this.roles = roles;
        this.authorities = authorities;
    }

    public AuthUserDetails(Integer userId, String username, String tenantCode, Integer tenantId, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.tenantCode = tenantCode;
        this.tenantId = tenantId;
        this.roles = roles;
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
        return this.status.equalsIgnoreCase("enabled");
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status.equalsIgnoreCase("enabled");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status.equalsIgnoreCase("enabled");
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getClass().getName()).append(" [");
//        sb.append("Username=").append(this.username).append(", ");
//        sb.append("Password=[PROTECTED], ");
//        sb.append("Enabled=").append(true).append(", ");
//        sb.append("AccountNonExpired=").append(true).append(", ");
//        sb.append("CredentialsNonExpired=").append(true).append(", ");
//        sb.append("AccountNonLocked=").append(true).append(", ");
//        sb.append("Granted Authorities=").append(this.authorities).append("]");
//        return sb.toString();
//    }

}
