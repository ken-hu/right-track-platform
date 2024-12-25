package pers.ken.rt.starter.pbac.permission.api;

import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pers.ken.rt.starter.pbac.annotation.Rn;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: ResourceProvider
 * @Created: 2024/10/31
 * @Author ken
 */
public class ResourceProvider {

    private static final StandardReflectionParameterNameDiscoverer DISCOVERER = new StandardReflectionParameterNameDiscoverer();


    public static List<String> convertToResources(
            String platform,
            String service,
            Rn[] rns,
            Method method,
            Object[] args) {
        return Arrays.stream(rns)
                .map(rn -> convertToResource(platform, service, rn.id(), rn.value(), method, args))
                .toList();
    }

    public static String convertToResource(
            String platform,
            String service,
            String resourceId,
            String expression,
            Method method,
            Object[] args) {
        String parsedExpression = parseExpression(expression, method, args);
        return String.format("%s:%s:%s/%s", platform, service, resourceId, parsedExpression);
    }

    private static String parseExpression(String expression, Method method, Object[] args) {
        //获取被拦截方法参数名列表
        String[] paramNameArr = DISCOVERER.getParameterNames(method);
        if (null == paramNameArr) {
            return "";
        }
        //SPEL解析
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArr.length; i++) {
            context.setVariable(paramNameArr[i], args[i]);
        }
        return parser.parseExpression(expression).getValue(context, String.class);
    }
}
