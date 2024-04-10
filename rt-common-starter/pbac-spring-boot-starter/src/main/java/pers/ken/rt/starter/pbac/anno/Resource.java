package pers.ken.rt.starter.pbac.anno;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.*;

/**
 * <code> Resource </code>
 * <desc> Resource </desc>
 * <b>Creation Time:</b> 2022/8/5 9:49.
 *
 * @author Ken.Hu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {
    String id();

    @Language("SpEL")
    String rn() default "";
}
