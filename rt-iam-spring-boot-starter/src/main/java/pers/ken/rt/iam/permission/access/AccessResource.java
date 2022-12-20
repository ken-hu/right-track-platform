package pers.ken.rt.iam.permission.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <code> Resource </code>
 * <desc> Resource </desc>
 * <b>Creation Time:</b> 2022/8/5 9:49.
 *
 * @author Ken.Hu
 */
@Target({})
@Retention(RUNTIME)
public @interface AccessResource {

    String resource();

    String resourceValue() default "";

}
