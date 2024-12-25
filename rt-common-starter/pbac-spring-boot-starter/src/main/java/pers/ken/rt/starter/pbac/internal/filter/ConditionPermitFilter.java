package pers.ken.rt.starter.pbac.internal.filter;

import org.springframework.util.CollectionUtils;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.Map;

/**
 * @ClassName: ConditionPermitFilter
 * @Created: 2024-04-01 14:54:15
 * @Description:
 * @Author ken
 */
public class ConditionPermitFilter implements PermitFilter {
    @Override
    public boolean matchCheck(PolicyContext policyContext, PolicyDocument.Statement policyStatement) {
        Map<String, Object> conditions = policyStatement.getConditions();
        if (!CollectionUtils.isEmpty(conditions)) {
            conditions.forEach((key, value) -> {
            });
        }
        // Not supported yet
        return true;
    }
}
