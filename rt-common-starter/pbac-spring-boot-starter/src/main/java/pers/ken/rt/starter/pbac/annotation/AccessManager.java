package pers.ken.rt.starter.pbac.annotation;

import java.lang.annotation.*;

/**
 * <code> AccessManagement </code>
 * <desc>
 * <p>
 * {#serviceId}:{#resourceType}:{#region}:[{#resourceId}/{#value}]
 * map:poi:#{adcode}:adcode/*\/icCode/*
 * <p>
 * <p/>
 * </desc>
 * <b>Creation Time:</b> 2022/8/2 13:54.
 *
 * @author Ken.Hu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessManager {
    String action() default "";

    Rn[] value() default {};
}
