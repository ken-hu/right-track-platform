package pers.ken.rt.common.web;

import java.lang.annotation.*;
/**
 * <code> ResponseFormat </code>
 * <desc> ResponseFormat </desc>
 * <b>Creation Time:</b> 2022/2/26 0:14.
 *
 * @author _Ken.Hu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseFormatIgnore {
}