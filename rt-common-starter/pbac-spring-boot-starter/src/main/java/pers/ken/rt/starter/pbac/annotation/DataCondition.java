package pers.ken.rt.starter.pbac.annotation;

import pers.ken.rt.starter.pbac.permission.data.DataConditionGenerator;
import pers.ken.rt.starter.pbac.permission.data.WhereInConditionGenerator;

import java.lang.annotation.*;

/**
 * @ClassName: DataScope
 * @Created: 2024/11/5 11:04
 * @Author ken
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataCondition {
    String value();

    Class<? extends DataConditionGenerator> conditionGenerator() default WhereInConditionGenerator.class;
}
