package pers.ken.rt.starter.pbac.internal.filter;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.List;

/**
 * @ClassName: ActionPermitFilter
 * @Created: 2024-04-01 14:36:49
 * @Description:
 * @Author ken
 */
public class ActionPermitFilter implements PermitFilter {
    @Override
    public boolean matchCheck(PolicyContext context, PolicyDocument.Statement policyStatement) {
        // 获取当前请求的action
        String requestAction = context.getRequestAction();
        List<String> policyActions = policyStatement.getActions();
        return actionPredicate(requestAction, policyActions);
    }

    private boolean actionPredicate(String requestAction, List<String> policyActions) {
        for (String policyAction : policyActions) {
            boolean match = actionMatch(requestAction, policyAction);
            // 任意命中一个Action则返回继续执行下一个Filter
            if (match) {
                return true;
            }
        }
        return false;
    }

    private boolean actionMatch(String requestAction, String policyAction) {
        if (policyAction.equalsIgnoreCase(requestAction)) {
            return true;
        }
        PathPattern parse = PathPatternParser.defaultInstance.parse(policyAction);
        return parse.matches(PathContainer.parsePath(requestAction));
    }
}
