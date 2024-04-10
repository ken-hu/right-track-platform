package pers.ken.rt.starter.pbac.internal;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.starter.pbac.anno.Resource;
import pers.ken.rt.starter.pbac.exception.AccessDeniedException;
import pers.ken.rt.starter.pbac.internal.Statement.Effect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creation Time: 2022/11/14 15:27.
 *
 * @author _Ken.Hu
 */
@Getter
public class PolicyContext {
    private PolicyContext(String serviceName, List<PolicyDocument> policies, String action, List<String> resources) {
        this.policies = policies;
        this.serviceName = serviceName;
        this.action = action;
        this.resources = resources;
    }

    private final List<PolicyDocument> policies;
    private final String serviceName;
    private final String action;
    private final List<String> resources;


    /**
     * Init policy context.
     *
     * @param serviceName the service name
     * @param policies    the policies
     * @return the policy context
     */
    public static PolicyContext init(String serviceName, List<PolicyDocument> policies, String action, List<String> resources) {
        return new PolicyContext(serviceName, policies, action, resources);
    }

    public static PolicyContext init(String serviceName, List<PolicyDocument> policies, String action) {
        return new PolicyContext(serviceName, policies, action, new ArrayList<>());
    }


    /**
     * Permit access boolean.
     *
     * @param targetAction the target action
     * @return the boolean
     */
    public boolean permitAccess(String targetAction) {
        return permitAccess(targetAction, true);
    }

    /**
     * Permit access boolean.
     *
     * @param action   the target action
     * @param throwOut the throw out
     * @return the boolean
     */
    public boolean permitAccess(String action, boolean throwOut) {
        return doPermitAccess(action, throwOut);
    }

    /**
     * Permit access boolean.
     *
     * @param targetAction the target action
     * @param targetRn     the target rn
     * @return the boolean
     */
    public boolean permitAccess(String targetAction, List<String> targetRn) {
        return permitAccess(targetAction, targetRn, true);
    }


    /**
     * Permit access boolean.
     *
     * @param targetAction the target action
     * @param targetRn     the target rn
     * @param throwOut     the throw out
     * @return the boolean
     */
    public boolean permitAccess(String targetAction, List<String> targetRn, boolean throwOut) {
        String target = this.serviceName + ":" + targetAction;
        return doPermitAccess(target, targetRn, throwOut);
    }

    private List<Statement> getStatements(PolicyDocument document, Statement.Effect effect) {
        return document.getStatements().stream().filter(x -> x.getEffect().equals(effect)).toList();
    }

    private boolean doPermitAccess(String action, boolean throwOut) {
        String target = this.serviceName + ":" + action;
        //1 r.userId == p.userId &&
        //2 r.act == p.act &&
        //3 keyMatch2(r.obj,p.obj) && resourceMatch(r.res,p.res)
        for (PolicyDocument policy : policies) {
            List<Statement> denyStatements = getStatements(policy, Statement.Effect.Deny);
            List<Statement> allowStatements = getStatements(policy, Statement.Effect.Allow);
            // 命中deny的直接优先拒绝
            boolean deny = actionPredicate(denyStatements, action);
            if (deny) {
                if (throwOut) {
                    throw new AccessDeniedException("action denied,policy:%s | statement.effect:%s | action:%s".formatted(policy.getName(), Effect.Deny, target));
                }
                return false;
            }
            boolean allow = actionPredicate(allowStatements, action);
            if (allow) {
                return true;
            }
        }
        if (throwOut) {
            throw new AccessDeniedException("action denied,no policy match action:%s".formatted(target));
        }
        return false;
    }

    private boolean doPermitAccess(String action, List<String> targetRn, boolean throwOut) {
        String target = this.serviceName + ":" + action;
        //1 r.userId == p.userId &&
        //2 r.act == p.act &&
        //3 keyMatch2(r.obj,p.obj) && resourceMatch(r.res,p.res)
        for (PolicyDocument policy : policies) {
            List<Statement> denyStatements = getStatements(policy, Statement.Effect.Deny);
            List<Statement> allowStatements = getStatements(policy, Statement.Effect.Allow);
            boolean deny = rnPredicate(denyStatements, action, Effect.Deny, targetRn, throwOut);
            if (deny) {
                if (throwOut) {
                    throw new AccessDeniedException("action denied,policy:%s | statement.effect:%s | action:%s".formatted(policy.getName(), Effect.Deny, target));
                }
                return false;
            }
            boolean allow = rnPredicate(allowStatements, action, Effect.Allow, targetRn, throwOut);
            if (allow) {
                return true;
            }

        }
        if (throwOut) {
            throw new AccessDeniedException("action denied,no policy match action:%s".formatted(target));
        }
        return false;
    }


    private boolean actionPredicate(List<Statement> statements, String target) {
        for (Statement statement : statements) {
            List<String> actions = statement.getActions();
            for (String action : actions) {
                return actionMatch(action, target);
            }
        }
        return false;
    }

    private boolean rnPredicate(List<Statement> statements, String action, Statement.Effect effect, List<String> resourceNames, boolean throwOut) throws AccessDeniedException {
        for (Statement statement : statements) {
            List<String> policyActions = statement.getActions();
            for (String policyAction : policyActions) {
                if (actionMatch(policyAction, action)) {
                    List<String> ownResources = statement.getResources();
                    return resourceNames.stream()
                            .allMatch(rn ->
                                    {
                                        boolean match = ownResources.stream()
                                                .anyMatch(own -> rnMatch(own, rn));
                                        switch (effect) {
                                            case Allow -> {
                                                if (!match && throwOut) {
                                                    throw new AccessDeniedException("Action denied,no policy match resource:[%s]".formatted(rn));
                                                }
                                            }
                                            case Deny -> {
                                                if (match && throwOut) {
                                                    throw new AccessDeniedException("Action denied,resource:[%s] access reject".formatted(rn));
                                                }
                                            }
                                        }
                                        return match;
                                    }
                            );
                }
            }
        }
        return false;
    }

    /**
     * Generate resource list.
     *
     * @param resources the resources
     * @param method    the method
     * @param args      the args
     * @return the list
     */
    public List<String> generateRn(Resource[] resources, Method method, Object[] args) {
        List<List<String>> resourcesTwoTier = Arrays.stream(resources).map(
                r -> {
                    String expressResolved = parseExpression(r.rn(), method, args);
                    return generateRn(r.id(), expressResolved);
                }
        ).toList();
        List<String> resultList = new ArrayList<>();
        recursion(resourcesTwoTier, 0, new ArrayList<>(), resultList);
        return resultList.stream().map(
                r -> "%s:%s".formatted(this.serviceName, r)
        ).toList();
    }

    private static void recursion(List<List<String>> allList, int index, List<String> currentCombination, List<String> resultList) {
        if (index == allList.size()) {
            // 递归终止条件：已经遍历完所有子列表，将当前组合添加到结果列表中
            resultList.add(String.join("/", currentCombination));
            return;
        }

        List<String> currentList = allList.get(index);
        for (String item : currentList) {
            currentCombination.add(item);
            recursion(allList, index + 1, currentCombination, resultList);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private String parseExpression(String expressionString, Method method, Object[] args) {
        //获取被拦截方法参数名列表
        StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
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


    /**
     * exp--  id: "icCode",resource:"1,2,3" convert to resources:["icCode/1","icCode/2","icCode/3"]
     *
     * @param resourceId the resource id
     * @param resource   the resource
     * @return list list
     */
    private static List<String> generateRn(String resourceId, String resource) {
        String[] element = StringUtils.split(resource, ",");
        return Arrays.stream(element).map(
                e -> "%s/%s".formatted(resourceId, e)
        ).toList();
    }

    /**
     * Resource name match boolean.
     *
     * @param onwRn    the onw rn
     * @param targetRn the target rn
     * @return the boolean
     */
    private static boolean rnMatch(String onwRn, String targetRn) {
        PathPattern parse = PathPatternParser.defaultInstance.parse(onwRn);
        return parse.matches(PathContainer.parsePath(targetRn));
    }


    private static boolean actionMatch(String policyAction, String action) {
        if (policyAction.equalsIgnoreCase(action)) {
            return true;
        }
        PathPattern parse = PathPatternParser.defaultInstance.parse(policyAction);
        return parse.matches(PathContainer.parsePath(action));
    }

}
