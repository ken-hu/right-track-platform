package pers.ken.rt.starter.pbac.internal.filter;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.starter.pbac.exception.AccessDeniedException;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;
import pers.ken.rt.starter.pbac.internal.Statement;
import pers.ken.rt.starter.pbac.internal.Statement.Effect;

import java.util.List;

/**
 * @ClassName: ActionPermitFilter
 * @Created: 2024-04-01 14:36:49
 * @Description:
 * @Author ken
 */
public class ActionPermitFilter implements PermitFilter {
    @Override
    public void doCheck(PolicyContext policyContext) {
        String serviceName = policyContext.getServiceName();
        String action = policyContext.getAction();

        String target = serviceName + ":" + action;
        //1 r.userId == p.userId &&
        //2 r.act == p.act &&
        //3 keyMatch2(r.obj,p.obj) && resourceMatch(r.res,p.res)
        for (PolicyDocument policy : policyContext.getPolicies()) {
            List<Statement> denyStatements = getStatements(policy, Statement.Effect.Deny);
            List<Statement> allowStatements = getStatements(policy, Statement.Effect.Allow);
            // 命中deny的直接优先拒绝
            boolean deny = actionPredicate(denyStatements, action);
            if (deny) {
                throw new AccessDeniedException("action denied,policy:%s | statement.effect:%s | action:%s".formatted(policy.getName(), Effect.Deny, target));
            }
            boolean allow = actionPredicate(allowStatements, action);
            if (allow) {
                return;
            }
        }
        throw new AccessDeniedException("action denied,no policy match action:%s".formatted(target));
    }

    private List<Statement> getStatements(PolicyDocument document, Statement.Effect effect) {
        return document.getStatements().stream().filter(x -> x.getEffect().equals(effect)).toList();
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

    private static boolean actionMatch(String policyAction, String action) {
        if (policyAction.equalsIgnoreCase(action)) {
            return true;
        }
        PathPattern parse = PathPatternParser.defaultInstance.parse(policyAction);
        return parse.matches(PathContainer.parsePath(action));
    }
}
