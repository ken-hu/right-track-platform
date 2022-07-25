package pers.ken.rt.test.iam;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import pers.ken.rt.iam.internal.Policy;
import pers.ken.rt.iam.permission.data.IDataProvider;
import pers.ken.rt.iam.utils.PolicyResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code> DefaultDataProvider </code>
 * <desc> DefaultDataProvider </desc>
 * <b>Creation Time:</b> 2022/8/24 15:08.
 *
 * @author Ken.Hu
 */
public class TestDataProvider implements IDataProvider {

    public SQLExpr sqlCondition(String resourceId){
        List<Policy> policies = new ArrayList<>();
        List<TestResource> testResources = PolicyResolver.resolveResource(policies, new TestConvert());
        // 去重
        List<SQLExpr> values = testResources.stream()
                .map(TestResource::getValue)
                .distinct()
                .map(SQLCharExpr::new)
                .collect(Collectors.toList());
        SQLInListExpr sqlInListExpr = new SQLInListExpr();
        sqlInListExpr.setTargetList(Collections.unmodifiableList(values));
        return sqlInListExpr;
    }
}
