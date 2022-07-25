package pers.ken.rt.iam.permission.access;

import lombok.AllArgsConstructor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pers.ken.rt.iam.AccessManagementProperties;
import pers.ken.rt.iam.internal.Policy;
import pers.ken.rt.iam.internal.PolicyGetHandler;
import pers.ken.rt.iam.internal.ResourceConvert;

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
public class AccessManagementSupport {
    private PolicyGetHandler policyGetHandler;
    private ResourceConvertRegistry registry;
    private AccessManagementProperties accessManagementProperties;

    public ResourceConvert<?> getConvert(String resourceType) {
        return registry.getConvert(resourceType);
    }

    public List<Policy> userPolicies() {
        return policyGetHandler.userPolicies();
    }

    public String genResource(AccessResource[] accessResources, Method method, Object[] args) {
        String rn = Arrays.stream(accessResources)
                .map(r -> r.resource() + "/" + parseExpression(r.resourceValue(), method, args))
                .collect(Collectors.joining("/"));

        return accessManagementProperties.getServiceName() + ":" + rn;
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
}
