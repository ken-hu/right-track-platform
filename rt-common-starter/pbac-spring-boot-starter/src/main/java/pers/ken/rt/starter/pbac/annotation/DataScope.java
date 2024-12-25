package pers.ken.rt.starter.pbac.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: DataScope
 * @Created: 2024/11/5 11:04
 * @Author ken
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    String[] tables();
}
