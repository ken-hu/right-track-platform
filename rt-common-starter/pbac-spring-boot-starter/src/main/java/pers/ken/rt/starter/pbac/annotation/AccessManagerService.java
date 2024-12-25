package pers.ken.rt.starter.pbac.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: AccessControlService
 * @Created: 2024/10/31
 * @Author ken
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessManagerService {
    String value() default "default";
}
