package pers.ken.rt.iam.permission.access;

import java.lang.annotation.*;

/**
 * <code> AccessManagement </code>
 * <desc>
 * <p>
 * {#serviceId}:{#resourceType}:{#region}:[{#resourceId}/{#resourceValue}]
 * map:poi:#{adcode}:adcode/*\/icCode/*
 * <p>
 * <p/>
 * </desc>
 * <b>Creation Time:</b> 2022/8/2 13:54.
 *
 * @author Ken.Hu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessManagement {
    /**
     * Action string.
     *
     * @return the string
     */
    String action();

    /**
     * 预留字段，匹配Code
     *
     * @return the string
     */
    String mappingCode() default "";

    String resource() default "";

    /**
     * Resources access resource [ ].
     *
     * @return the access resource [ ]
     */
    AccessResource[] resources() default {};
}
