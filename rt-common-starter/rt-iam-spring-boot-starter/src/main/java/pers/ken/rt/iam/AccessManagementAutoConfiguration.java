package pers.ken.rt.iam;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * <code> IAMAutoConfiguration </code>
 * <desc> IAMAutoConfiguration </desc>
 * <b>Creation Time:</b> 2022/8/2 12:01.
 *
 * @author Ken.Hu
 */
@ConditionalOnProperty(
        prefix = "api-access.auth",
        value = "enabled",
        havingValue = "true"
)
@Import(AccessManagementConfiguration.class)
public class AccessManagementAutoConfiguration {

}
