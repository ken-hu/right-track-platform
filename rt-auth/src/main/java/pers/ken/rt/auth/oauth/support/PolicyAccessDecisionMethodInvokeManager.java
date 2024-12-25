package pers.ken.rt.auth.oauth.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @ClassName: PolicyAccessDecisionMethodInvokeManager
 * @Created: 2024/12/9 16:09
 * @Author ken
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyAccessDecisionMethodInvokeManager implements AuthorizationManager<MethodInvocation> {
    @Override
    public void verify(Supplier<Authentication> authentication, MethodInvocation object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {
        log.info("authentication:{}", authentication);
        return new AuthorizationDecision(true);
    }
}
