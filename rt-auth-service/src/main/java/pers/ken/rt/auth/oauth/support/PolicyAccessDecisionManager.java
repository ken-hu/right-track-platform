package pers.ken.rt.auth.oauth.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Name: PolicyAccessDecisionManager
 * Creation Time: 2023/1/8 22:35.
 *
 * @author Ken
 */
@Component
@Slf4j
public class PolicyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("authentication:{}", authentication);
        log.info("object:{}", object);
        log.info("configAttributes:{}", configAttributes);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return Boolean.TRUE;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Boolean.TRUE;
    }
}
