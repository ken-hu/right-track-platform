package pers.ken.rt.starter.pbac.internal.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PermitCheckFilter
 * @Created: 2024-04-01 14:30:44
 * @Description:
 * @Author ken
 */
public class PermitFilterChain {
    private final List<PermitFilter> filters = new ArrayList<>();

    public PermitFilterChain addFilter(PermitFilter handler) {
        filters.add(handler);
        return this;
    }
}
