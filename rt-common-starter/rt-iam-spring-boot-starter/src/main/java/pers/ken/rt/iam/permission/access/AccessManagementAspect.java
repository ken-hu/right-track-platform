package pers.ken.rt.iam.permission.access;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pers.ken.rt.iam.internal.AuthContext;
import pers.ken.rt.iam.internal.Policy;
import pers.ken.rt.iam.internal.ResourceAccessDetail;
import pers.ken.rt.iam.permission.data.IDataProvider;
import pers.ken.rt.iam.utils.PolicyResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        try {
            List<Policy> policies = accessManagementSupport.userPolicies();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            AccessManagement annotation = method.getAnnotation(AccessManagement.class);
            AccessResource[] accessResources = annotation.resources();
            String resource = accessManagementSupport.getResourceName(accessResources, method, joinPoint.getArgs());
            String actionName = accessManagementSupport.getActionName(annotation.action());
            List<IDataProvider> converts = Arrays.stream(accessResources)
                    .map(AccessResource::resource)
                    .map(x -> accessManagementSupport.getConvert(x))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            AuthContext.set(
                    ResourceAccessDetail.builder()
                            .policies(policies)
                            .dataProviders(converts)
                            .build()
            );

            boolean isPermit =
                    PolicyResolver.isPermit(policies,
                            resource,
                            actionName);

            if (!isPermit) {
                throw new AccessDenyException("Access Deny");
            }
            return joinPoint.proceed();
        } finally {
            AuthContext.remove();
        }
    }

}
