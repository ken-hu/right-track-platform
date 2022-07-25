package pers.ken.rt.common.iam;

import com.google.common.base.CaseFormat;
import pers.ken.rt.common.iam.rn.ResourceId;
import pers.ken.rt.common.iam.rn.Rn;
import pers.ken.rt.common.iam.rn.RnConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code> PolicyUtils </code>
 * <desc> PolicyUtils </desc>
 * <b>Creation Time:</b> 2022/3/4 0:28.
 *
 * @author _Ken.Hu
 */
public class PolicyUtils {

    private PolicyUtils() {
    }

    public static boolean isParamsCheckPermit(UserDetail userDetail, Map<String, Object> paramsMap) {
        return Boolean.FALSE;
    }

    public static <T extends ResourceId> List<T> castToResource(List<String> resources, String resourceType, RnConverter<T> rnConverter) {
        return resources.stream()
                .map(Rn::fromString)
                .filter(k -> k.getResourceType().equals(resourceType))
                .map(rnConverter::convertRn)
                .collect(Collectors.toList());
    }

    public static boolean isActionPermit(String action, List<Policy> policies) {
        for (Policy policy : policies) {
            for (Statement statement : policy.getStatements()) {
                if (Statement.Effect.Allow.equals(statement.getEffect())) {
                    for (Action ownAction : statement.getActions()) {
                        if (ownAction.getActionName().equalsIgnoreCase(action)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String uriToActionCode(String service, String method, String uri) {
        String res = method + "_" + uri.replace("/", "_");
        res = res.replace("-", "_");
        res = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, res);
        return String.format("%s:%s", service, res);
    }
}
