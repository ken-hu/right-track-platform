package pers.ken.rt.starter.pbac.internal.filter;

import pers.ken.rt.starter.pbac.exception.AccessDeniedException;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;
import pers.ken.rt.starter.pbac.internal.Statement;
import pers.ken.rt.starter.pbac.internal.Statement.Effect;

import java.util.List;

/**
 * @ClassName: ResourcePermitFilter
 * @Created: 2024-04-01 14:53:53
 * @Description:
 * @Author ken
 */
public class ResourcePermitFilter implements PermitFilter{
    @Override
    public void doCheck(PolicyContext policyContext) {
        String serviceName = policyContext.getServiceName();
        String action = policyContext.getAction();
        String target = serviceName + ":" + action;
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
}
