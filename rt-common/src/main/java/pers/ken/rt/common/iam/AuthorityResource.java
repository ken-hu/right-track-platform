package pers.ken.rt.common.iam;

import java.lang.annotation.*;

/**
 * <code> Authority </code>
 * <desc> Authority </desc>
 * <b>Creation Time:</b> 2022/3/1 23:47.
 *
 * @author _Ken.Hu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorityResource {
    String value();
}
