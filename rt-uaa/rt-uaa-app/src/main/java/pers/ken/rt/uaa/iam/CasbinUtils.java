package pers.ken.rt.uaa.iam;

import com.google.common.io.Resources;
import org.casbin.jcasbin.main.Enforcer;

/**
 * <code> CasbinUtils </code>
 * <desc> CasbinUtils </desc>
 * <b>Creation Time:</b> 2022/6/24 14:30.
 *
 * @author Ken.Hu
 */
public class CasbinUtils {
    public static Enforcer enforcerCreate() {
        String modelPath = Resources.getResource("casbin/model.conf").getPath();
        String policyPath = Resources.getResource("casbin/policy.csv").getPath();
        return new Enforcer(modelPath, policyPath);
    }

    public static boolean validate(String userId, String resource, String action, String modelPath, String policyPath) {
        Enforcer enforcer = new Enforcer(modelPath, policyPath);
        return enforcer.enforce(userId, resource, action);
    }

    public static boolean validate(String userId, String resource, String action) {
        String modelPath = Resources.getResource("casbin/model.conf").getPath();
        String policyPath = Resources.getResource("casbin/policy.csv").getPath();
        return validate(userId, resource, action, modelPath, policyPath);
    }
}
