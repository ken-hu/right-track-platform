package pers.ken.rt.pbac.permission.access;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.pbac.exception.AccessDenyException;
import pers.ken.rt.pbac.internal.AuthContextHolder;
import pers.ken.rt.pbac.internal.Policy;
import pers.ken.rt.pbac.internal.PolicyContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private AccessControlSupport accessControlSupport;

    /**
     * Point.
     */
    @Pointcut("@annotation(pers.ken.rt.pbac.permission.access.AccessControl)")
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
            List<Policy> policies = accessControlSupport.userPolicies();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            AccessControl annotation = method.getAnnotation(AccessControl.class);
            Resource[] resources = annotation.resources();
            Map<String, String> accessResource = Arrays.stream(resources)
                    .collect(
                            Collectors.toMap(Resource::id,
                                    resource -> accessControlSupport.getRequestedResource(resource, method, joinPoint.getArgs())
                            )
                    );
            if (!CollectionUtils.isEmpty(policies)) {
                AuthContextHolder.set(new PolicyContext(policies, accessResource));
            }
            List<String> requestedResources = new ArrayList<>(accessResource.values());
            boolean isPermit =
                    accessControlSupport.isPermit(policies,
                            requestedResources,
                            annotation.actionId());
            log.debug("Api methodName:{} | isPermit:{} | requestedResources={} actionId={}", method.getName(), isPermit, requestedResources, annotation.actionId());
            if (!isPermit) {
                throw new AccessDenyException("Access Deny");
            }
            return joinPoint.proceed();
        } finally {
            AuthContextHolder.remove();
        }
    }
}

