package pers.ken.rt.starter.pbac.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pers.ken.rt.starter.pbac.internal.Rn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: PolicyUtils
 * @Created: 2024/6/6
 * @Author ken
 */
public class PolicyUtils {

    private static final StandardReflectionParameterNameDiscoverer DISCOVERER = new StandardReflectionParameterNameDiscoverer();

    private PolicyUtils() {

    }

    /**
     * Generate resource list.
     *
     * @param projectName the projectName
     * @param method      the method
     * @param args        the args
     * @return the list
     */
    public static List<String> generateRn(String projectName, String serviceName, Method method, Object[] args) {
        List<String> rns = new ArrayList<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Object param = args[i];
            if (param == null) {
                continue;
            }

            Annotation[] paramAnn = parameterAnnotations[i];
            if (paramAnn.length == 0) {
                continue;
            }

            for (Annotation annotation : paramAnn) {
                if (annotation.annotationType().equals(Rn.class)) {
//                    rns.add()
                }
                break;
            }

        }
//        return Arrays.stream(resources).map(
//                r -> {
//                    String expressResolved = parseExpression(r.rn(), method, args);
//                    // exp: rid:category val:1,2,3 rnRaw:["icCode/1","icCode/2","icCode/3"]
//                    List<String> rnRaw = generateRn(serviceName, r.id(), expressResolved);
//                    return Rn.builder()
//                            .rid(r.id())
//                            .val(expressResolved)
//                            .rnRaw(rnRaw)
//                            .build();
//                }
//        ).collect(Collectors.toMap(Rn::getRid, Function.identity(), (oldVal, newVal) -> newVal));
        return List.of();
    }

    private static String parseExpression(String expressionString, Method method, Object[] args) {
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
        return parser.parseExpression(expressionString).getValue(context, String.class);
    }


    /**
     * exp--  id: "icCode",resource:"1,2,3" convert to resources:["icCode/1","icCode/2","icCode/3"]
     *
     * @param resourceId the resource id
     * @param resource   the resource
     * @return list list
     */
    private static List<String> generateRn(String serviceName, String resourceId, String resource) {
        String[] element = StringUtils.split(resource, ",");
        return Arrays.stream(element).map(
                e -> "%s:%s/%s".formatted(serviceName, resourceId, e)
        ).toList();
    }

}
