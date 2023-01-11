package pers.ken.rt.pbac.permission.access;

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
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessControl {
    /**
     * Action string.
     *
     * @return the string
     */
    String actionId();

    /**
     * Resources access id [ ].
     *
     * @return the access id [ ]
     */
    Resource[] resources() default {};
}
