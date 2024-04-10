package pers.ken.rt.starter.pbac.permission.access;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pers.ken.rt.starter.pbac.AccessControlProperties;
import pers.ken.rt.starter.pbac.anno.AccessControl;
import pers.ken.rt.starter.pbac.anno.Resource;
import pers.ken.rt.starter.pbac.exception.AccessDeniedException;
import pers.ken.rt.starter.pbac.exception.AccessControlException;
import pers.ken.rt.starter.pbac.internal.PermissionExceptionTranslation;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyContextHolder;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;
import pers.ken.rt.starter.pbac.internal.PolicyProvider;

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
@AllArgsConstructor
@Slf4j
public class AccessControlAspect {
    private AccessControlProperties properties;
    private PolicyProvider policyProvider;
    private PermissionExceptionTranslation exceptionTranslation;

    /**
     * Point.
     */
    @Pointcut("@annotation(pers.ken.rt.starter.pbac.anno.AccessControl)")
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
            List<PolicyDocument> policies = policyProvider.loadPolicies();
            PolicyContext context = PolicyContext.init(properties.getServiceName(), policies);

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            AccessControl annotation = method.getAnnotation(AccessControl.class);
            Resource[] resources = annotation.resources();
            try {
                boolean permitAccess;
                String action = annotation.action();
                if (ArrayUtils.isEmpty(resources)) {
                    permitAccess = context.permitAccess(action);
                    log.debug("api method:{} | permit:{} | action={}",
                            method.getName(),
                            permitAccess,
                            annotation.action());
                } else {
                    List<String> resolveResources = context.generateRn(resources, method, joinPoint.getArgs());
                    permitAccess = context.permitAccess(action, resolveResources);
                    log.debug("api method:{} | permit:{} | rn={} | action={}",
                            method.getName(),
                            permitAccess,
                            String.join(",", resolveResources),
                            annotation.action());
                }
                if (!permitAccess) {
                    throw new AccessDeniedException("Forbidden");
                }
            } catch (AccessControlException e) {
                throw exceptionTranslation.resolve(e);
            } catch (Exception e) {
                log.error("Policy resolve error", e);
                throw exceptionTranslation.resolve(e);
            } finally {
                PolicyContextHolder.set(context);
            }
            return joinPoint.proceed();
        } finally {
            PolicyContextHolder.remove();
        }
    }

}

