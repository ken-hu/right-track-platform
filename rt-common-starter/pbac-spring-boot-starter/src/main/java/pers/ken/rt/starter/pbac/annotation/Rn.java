package pers.ken.rt.starter.pbac.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Rn
 * @Created: 2024/7/25
 * @Author ken
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Rn {
    String id();

    String value();
}
