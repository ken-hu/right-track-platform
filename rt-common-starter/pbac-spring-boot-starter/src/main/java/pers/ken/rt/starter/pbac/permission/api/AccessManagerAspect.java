package pers.ken.rt.starter.pbac.permission.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.starter.pbac.PbacProperties;
import pers.ken.rt.starter.pbac.annotation.AccessManager;
import pers.ken.rt.starter.pbac.annotation.AccessManagerService;
import pers.ken.rt.starter.pbac.core.DynamicExpressionsProvider;
import pers.ken.rt.starter.pbac.core.PermissionExceptionTranslation;
import pers.ken.rt.starter.pbac.core.PolicyProvider;
import pers.ken.rt.starter.pbac.exception.BaseAccessManagerException;
import pers.ken.rt.starter.pbac.exception.PolicyAccessDeniedException;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyContextHolder;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;
import pers.ken.rt.starter.pbac.internal.filter.PermitFilterChain;

import java.lang.reflect.Method;
import java.util.List;


/**
 * <code> AccessManagementAspect </code>
 * <desc> AccessManagementAspect </desc>
 * <b>Creation Time:</b> 2022/8/4 15:05.
 *
 * @author Ken.Hu
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AccessManagerAspect {
    private final PbacProperties properties;
    private final PolicyProvider policyProvider;
    private final PermissionExceptionTranslation exceptionTranslation;
    private final DynamicExpressionsProvider dynamicExpressionsProvider;

    /**
     * Pointcut
     */
    @Pointcut("@annotation(pers.ken.rt.starter.pbac.annotation.AccessManager)")
    public void point() {
    }

    /**
     * Invoke object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("point()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            AccessManager annotation = method.getAnnotation(AccessManager.class);
            if (null == annotation) {
                return joinPoint.proceed();
            }
            // Init context
            List<PolicyDocument> policies = loadPolicies();

            String service = getPbacServiceName(joinPoint);

            String action = getRequestAction(service, method);

            List<String> resources = getRequestResources(joinPoint.getArgs(), service, annotation, method);

            PolicyContext context = PolicyContext.init(service, policies)
                    .action(action)
                    .resources(resources);

            try {
                //Deny Primary
                boolean denyMatch = checkPolicyMatch(PolicyDocument.Effect.Deny, context);
                if (denyMatch) {
                    throw new PolicyAccessDeniedException(String.format("Explicit Deny. Accessing %s", action));
                }
                // Allowed
                boolean allowMatch = checkPolicyMatch(PolicyDocument.Effect.Allow, context);
                if (allowMatch) {
                    log.debug("Allow to access. Accessing {} {} Match policy[{}::{}] statement[{}]",
                            action,
                            resources,
                            context.getMatchPolicyDocument().getId(),
                            context.getMatchPolicyDocument().getName(),
                            context.getMatchStatement().getId());
                    return joinPoint.proceed();
                }
                log.warn("Implicit Deny. Does not comply with any policy. Accessing {} resources {}", action, resources);
                throw new PolicyAccessDeniedException("Implicit Deny. Does not comply with any policy. Accessing %s".formatted(action));
            } catch (BaseAccessManagerException e) {
                throw exceptionTranslation.resolve(e);
            } catch (Exception e) {
                log.error("Policy resolve error", e);
                throw exceptionTranslation.resolve(e);
            } finally {
                PolicyContextHolder.set(context);
            }
        } finally {
            PolicyContextHolder.remove();
        }
    }

    private String getRequestAction(String service, Method method) {
        return ActionProvider.convertURItoActionName(service, method);
    }

    private List<String> getRequestResources(Object[] args, String service, AccessManager annotation, Method method) {
        return ResourceProvider.convertToResources(
                properties.getPlatform(),
                service,
                annotation.value(),
                method,
                args);
    }

    private List<PolicyDocument> loadPolicies() {
        List<PolicyDocument> policies = policyProvider.loadMyPolicies();

        if (CollectionUtils.isEmpty(policies)) {
            throw new PolicyAccessDeniedException("User's policies is empty");
        }
        return policies;
    }

    private boolean checkPolicyMatch(PolicyDocument.Effect effect, PolicyContext context) {
        List<PolicyDocument> policies = context.getPolicies();
        for (PolicyDocument policy : policies) {
            for (PolicyDocument.Statement statement : policy.getStatements()) {
                if (effect.equals(statement.getEffect())) {
                    // 动态表达式解析 exp: rt:test:${authority_city} ->> ['rt:map:region:100100','rt:region:region:100200']
                    PolicyDocument.Statement parseStatement = dynamicExpressionsProvider.dynamicParserExpressions(statement);
                    PermitFilterChain permitFilterChain = PermitFilterChain.init(parseStatement);
                    boolean match = permitFilterChain.matchCheck(context, policy, parseStatement);
                    if (match) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String getPbacServiceName(ProceedingJoinPoint joinPoint) {
        AccessManagerService annotation = joinPoint.getTarget().getClass().getAnnotation(AccessManagerService.class);
        if (null != annotation) {
            return annotation.value();
        }
        return properties.getService();
    }
}
