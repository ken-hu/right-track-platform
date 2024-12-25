package pers.ken.rt.auth.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @ClassName: LoginLimitException
 * @Created: 2024/12/10 13:52
 * @Author ken
 */
public class LoginLimitException extends AuthenticationException {
    public LoginLimitException(String msg) {
        super(msg);
    }
}
