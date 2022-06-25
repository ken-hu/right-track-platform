package pers.ken.rt.uaa.iam;

import org.casbin.jcasbin.main.Enforcer;

/**
 * <code> CasbinUtils </code>
 * <desc> CasbinUtils </desc>
 * <b>Creation Time:</b> 2022/6/24 14:30.
 *
 * @author Ken.Hu
 */
public class CasbinUtils {
    public static boolean validate(String userId, String resource, String action, String modelPath, String policyPath) {
        Enforcer enforcer = new Enforcer(modelPath, policyPath);
        return enforcer.enforce(userId, resource, action);
    }
}
