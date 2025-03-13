package pers.ken.rt.starter.pbac.permission.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.starter.pbac.annotation.DataCondition;
import pers.ken.rt.starter.pbac.annotation.DataScope;
import pers.ken.rt.starter.pbac.annotation.DataScopes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: DataScopeAspect
 * @Created: 2025/3/12 16:58
 * @Author ken
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DataScopeAspect {

    @Pointcut("@annotation(pers.ken.rt.starter.pbac.annotation.DataScope)")
    public void dataScopePointcut() {
    }

    @Pointcut("@annotation(pers.ken.rt.starter.pbac.annotation.DataScopes)")
    public void dataScopesPointcut() {
    }

    @Pointcut("dataScopePointcut() || dataScopesPointcut()")
    public void pointcut() {
    }

    private final DataPermissionProvider dataPermissionProvider;

    @Around("pointcut()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ArrayList<DataScope> annotations = new ArrayList<>();
            DataScopes dataScopesAnno = method.getAnnotation(DataScopes.class);
            DataScope dataScopeAnno = method.getAnnotation(DataScope.class);

            if (null != dataScopesAnno) {
                DataScope[] value = dataScopesAnno.value();
                annotations.addAll(Arrays.stream(value).toList());
            }

            if (null != dataScopeAnno) {
                annotations.add(dataScopeAnno);
            }

            if (CollectionUtils.isEmpty(annotations)) {
                return joinPoint.proceed();
            }

            List<DataPermission> dataPermissions = annotations.stream().map(annotation -> {
                String table = annotation.table();
                DataCondition[] conditions = annotation.conditions();
                return dataPermissionProvider.loadDataPermission(table, conditions);
            }).toList();
            DataPermissionContextHolder.set(dataPermissions);
            return joinPoint.proceed();

        } catch (Exception e) {
            log.error("Policy resolve error", e);
        } finally {
            DataPermissionContextHolder.remove();
        }
        throw new IllegalAccessError("Get data permission failed");
    }
}
