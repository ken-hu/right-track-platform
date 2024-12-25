package pers.ken.rt.starter.pbac.internal.filter;

import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

/**
 * @ClassName: PermitFilter
 * @Created: 2024-04-01 14:32:04
 * @Description:
 * @Author ken
 */
public interface PermitFilter {

    boolean matchCheck(PolicyContext context, PolicyDocument.Statement policyStatement);

}
