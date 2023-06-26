package pers.ken.rt.gw.oauth.support;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Ken
 * @className: PolicyAuthorizationManager
 * @createdTime: 2023/3/27 22:38
 * @desc:
 */
@Component
@Slf4j
public class PolicyAuthorizationManager implements AuthorizationManager<MethodInvocation> {
    @Override
    public void verify(Supplier<Authentication> authentication, MethodInvocation object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {
//        ExpressionAttribute attribute = this.registry.getAttribute(mi);
//        if (attribute == ExpressionAttribute.NULL_ATTRIBUTE) {
//            return null;
//        }
//        EvaluationContext ctx = this.registry.getExpressionHandler().createEvaluationContext(authentication, mi);
//        boolean granted = ExpressionUtils.evaluateAsBoolean(attribute.getExpression(), ctx);
//        return new ExpressionAuthorizationDecision(granted, attribute.getExpression());
        log.info("authen:{}", authentication);
        log.info("methodInvocation:{}", object);
        return new AuthorizationDecision(true);
    }
}
