package pers.ken.rt.pbac.permission.access;

import lombok.AllArgsConstructor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;
import pers.ken.rt.pbac.AccessControlProperties;
import pers.ken.rt.pbac.internal.Policy;
import pers.ken.rt.pbac.internal.PolicyGetHandler;
import pers.ken.rt.pbac.utils.PolicyResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code> AccessManagementSupport </code>
 * <desc> AccessManagementSupport </desc>
 * <b>Creation Time:</b> 2022/8/22 16:47.
 *
 * @author Ken.Hu
 */
@AllArgsConstructor
public class AccessControlSupport {
    private PolicyGetHandler policyGetHandler;
    private AccessControlProperties accessControlProperties;

    /**
     * User policies list.
     *
     * @return the list
     */
    public List<Policy> userPolicies() {
        return policyGetHandler.loadPolicies();
    }

    /**
     * Gets id name.
     *
     * @param resources the access resources
     * @param method    the method
     * @param args      the args
     * @return the id name
     */
    public List<String> getRequestedResources(Resource[] resources, Method method, Object[] args) {
        return Arrays.stream(resources)
                .filter(a -> StringUtils.hasText(a.value()))
                .map(r -> String.format("%s:%s:%s/%s",
                        accessControlProperties.getServiceName(),
                        accessControlProperties.getResourceCode(),
                        r.id(),
                        parseExpression(r.value(), method, args))
                )
                .collect(Collectors.toList());
    }

    public String getRequestedResource(Resource resource, Method method, Object[] args) {
        if (!StringUtils.hasText(resource.value())) {
            return "";
        }
        return String.format("%s:%s:%s/%s",
                accessControlProperties.getServiceName(),
                accessControlProperties.getResourceCode(),
                resource.id(),
                parseExpression(resource.value(), method, args));
    }

    private String parseExpression(String expressionString, Method method, Object[] args) {
        //获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNameArr = discoverer.getParameterNames(method);
        if (null == paramNameArr) {
            return "";
        }
        //SPEL解析
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArr.length; i++) {
            context.setVariable(paramNameArr[i], args[i]);
        }
        return parser.parseExpression(expressionString).getValue(context, String.class);
    }

    public boolean isPermit(List<Policy> policies, List<String> requestedResources, String requestedAction) {
        return PolicyResolver.isPermit(policies, requestedResources, requestedAction);
    }
}
