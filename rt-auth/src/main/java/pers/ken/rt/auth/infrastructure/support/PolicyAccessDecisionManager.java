package pers.ken.rt.auth.infrastructure.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import pers.ken.rt.auth.domain.entity.aggregate.Policy;
import pers.ken.rt.auth.domain.repository.PolicyRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Name: PolicyAccessDecisionManager
 * Creation Time: 2023/1/8 22:35.
 *
 * @author Ken
 */
@Slf4j
//@Component
//@AllArgsConstructor
public class PolicyAccessDecisionManager implements AuthorizationManager<RequestAuthorizationContext> {
    private PolicyRepository policyRepository;

    /**
     * 确定是否应该为特定的身份验证和对象授予访问权。
     *
     * @param authentication              the {@link Supplier} of the {@link Authentication} to check
     * @param requestAuthorizationContext the {@link T} object to check
     */
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        log.info("authentication:{}", authentication.get());
        log.info("requestAuthorizationContext:{}", requestAuthorizationContext.toString());
        AuthorizationManager.super.verify(authentication, requestAuthorizationContext);
    }

    /**
     * 确定是否授予对特定身份验证和对象的访问权。
     *
     * @param authentication the {@link Supplier} of the {@link Authentication} to check
     * @param object         the {@link T} object to check
     * @return
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        // 身份认证成功的情况下
        Authentication auth = authentication.get();
        log.info("authentication:{}", auth);
        if (auth.isAuthenticated()) {
            HttpServletRequest request = object.getRequest();
            log.info("request:{}",request);
            Map<String, String> variables = object.getVariables();
            log.info("variables:{}",variables);
            return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }

    private void checkPolicies(Long userId){
        List<Policy> policies = policyRepository.findListByUserId(userId);
        policies.parallelStream()
                .forEach(policy->{
//                    policy.check()
                });
    }
}
