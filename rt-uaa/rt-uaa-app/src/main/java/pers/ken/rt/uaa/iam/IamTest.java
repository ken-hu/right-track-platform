package pers.ken.rt.uaa.iam;

import com.google.common.io.Resources;
import org.casbin.jcasbin.main.Enforcer;

import java.util.List;

/**
 * <code> IamTest </code>
 * <desc> IamTest </desc>
 * <b>Creation Time:</b> 2022/6/10 15:51.
 *
 * @author Ken.Hu
 */
public class IamTest {
    public static void main(String[] args) {
        String httpMethod = "Get";
        String action = "listAllCity";
        String resource = "mars:city/*";
        String userId = "ken";

        String modelPath = Resources.getResource("casbin/model.conf").getPath();
        String policyPath = Resources.getResource("casbin/policy.csv").getPath();
        Enforcer enforcer = new Enforcer(modelPath, policyPath);
        // user
        String sub = "ken";
        // resource
        String obj = "mars:adcode/1";
        // policy
        String act = "GetListAllCity";

        // 带参数校验
        if (enforcer.enforce(sub, obj, act)) {
            //校验通过
            List<List<String>> policy = enforcer.getPolicy();
            System.out.println(policy);

            List<List<String>> ken = enforcer.getFilteredNamedPolicy("p", 0, "ken");
            System.out.println(ken);

            List<List<String>> ken1 = enforcer.getFilteredPolicy( 2, "GetListAllCity");
            System.out.println(ken1);



        } else {
            throw new RuntimeException("访问受限");
        }

        // 只校验URI

    }
}
