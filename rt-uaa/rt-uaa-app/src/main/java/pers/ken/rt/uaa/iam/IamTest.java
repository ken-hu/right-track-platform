package pers.ken.rt.uaa.iam;

import com.google.common.io.Resources;
import org.casbin.jcasbin.exception.CasbinAdapterException;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.FilteredAdapter;
import org.springframework.util.StopWatch;

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
//        Enforcer enforcer = new Enforcer(modelPath,policyPath);
//        Enforcer enforcer = new Enforcer(modelPath,new TestAdapter());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Enforcer enforcer = new Enforcer(modelPath);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        enforcer.addPolicy("ken", "mars:popType/*", "PostMapDimAmapConfSave");
        enforcer.addPolicy("ken", "mars:adcode/*", "GetListAllCity");
        enforcer.addPolicy("liz", "mars:adcode/*", "GetListAllCity");
        enforcer.addPolicy("ken", "mars:adcode/3", "GetListAllCity");


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
//            List<List<String>> ken = enforcer.getFilteredNamedPolicy("p", 0, "ken");
//            System.out.println(ken);
            List<List<String>> ken1 = enforcer.getFilteredPolicy(0, "ken", "", "GetListAllCity");
            System.out.println(ken1);

        } else {
            throw new RuntimeException("访问受限");
        }


        // 只校验URI

    }

    public static class TestAdapter implements FilteredAdapter {

        @Override
        public void loadFilteredPolicy(Model model, Object o) throws CasbinAdapterException {

        }

        @Override
        public boolean isFiltered() {
            return false;
        }

        @Override
        public void loadPolicy(Model model) {

        }

        @Override
        public void savePolicy(Model model) {

        }

        @Override
        public void addPolicy(String s, String s1, List<String> list) {
            System.out.println("............ add policy ");
        }

        @Override
        public void removePolicy(String s, String s1, List<String> list) {

        }

        @Override
        public void removeFilteredPolicy(String s, String s1, int i, String... strings) {

        }
    }
}
