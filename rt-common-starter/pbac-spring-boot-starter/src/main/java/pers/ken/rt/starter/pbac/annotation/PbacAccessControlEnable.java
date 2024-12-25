package pers.ken.rt.starter.pbac.annotation;

import org.springframework.context.annotation.Import;
import pers.ken.rt.starter.pbac.ApiAccessManagerAutoConfiguration;

import java.lang.annotation.*;

/**
 * @ClassName: PbacAccessControlEnable
 * @Created: 2024/11/4 14:10
 * @Author ken
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ApiAccessManagerAutoConfiguration.class})
public @interface PbacAccessControlEnable {
}
