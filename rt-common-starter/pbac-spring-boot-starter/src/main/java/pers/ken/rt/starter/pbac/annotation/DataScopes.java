package pers.ken.rt.starter.pbac.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: DataScopes
 * @Created: 2025/3/12 16:03
 * @Author ken
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScopes {
    DataScope[] value();
}
