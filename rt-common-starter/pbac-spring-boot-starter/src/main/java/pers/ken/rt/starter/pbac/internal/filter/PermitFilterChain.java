package pers.ken.rt.starter.pbac.internal.filter;

import lombok.Getter;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PermitCheckFilter
 * @Created: 2024-04-01 14:30:44
 * @Description:
 * @Author ken
 */
@Getter
public class PermitFilterChain {
    private final List<PermitFilter> filters = new ArrayList<>();

    private PermitFilterChain() {
    }

    public static PermitFilterChain init() {
        PermitFilterChain permitFilterChain = new PermitFilterChain();
        permitFilterChain.filters.add(new ActionPermitFilter());
        return permitFilterChain;
    }


    public static PermitFilterChain init(PolicyDocument.Statement statement) {
        PermitFilterChain permitFilterChain = init();
        if (!CollectionUtils.isEmpty(statement.getResources())) {
            permitFilterChain.filters.add(new ResourcePermitFilter());
        }
        if (!CollectionUtils.isEmpty(statement.getConditions())) {
            permitFilterChain.filters.add(new ConditionPermitFilter());
        }
        return permitFilterChain;
    }

    private PermitFilterChain addResourceFilter() {
        this.filters.add(new ResourcePermitFilter());
        return this;
    }

    private PermitFilterChain addConditionFilter() {
        this.filters.add(new ConditionPermitFilter());
        return this;
    }

    public boolean matchCheck(PolicyContext context, PolicyDocument policy, PolicyDocument.Statement statement) {
        boolean match = this.filters.stream()
                .allMatch(filter -> filter.matchCheck(context, statement));
        if (match) {
            context.updateMatchPolicyDocument(policy, statement);
        }
        return match;
    }
}

