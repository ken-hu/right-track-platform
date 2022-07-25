package pers.ken.rt.iam.permission.access;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pers.ken.rt.iam.internal.Policy;
import pers.ken.rt.iam.utils.PolicyResolver;

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
public class AccessManagementAspect {
    private AccessManagementSupport accessManagementSupport;

    /**
     * Point.
     */
    @Pointcut("@annotation(pers.ken.rt.iam.permission.access.AccessManagement)")
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
        List<Policy> policies = accessManagementSupport.userPolicies();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AccessManagement annotation = method.getAnnotation(AccessManagement.class);
        AccessResource[] accessResources = annotation.resources();

        String resource = accessManagementSupport.genResource(accessResources, method, joinPoint.getArgs());

        boolean isPermit =
                PolicyResolver.isPermit(policies, resource, annotation.action());

        if (!isPermit) {
            throw new AccessDenyException("Access Deny");
        }
        return joinPoint.proceed();
    }

}
