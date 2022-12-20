package pers.ken.rt.test.iam;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import pers.ken.rt.iam.internal.AuthContext;
import pers.ken.rt.iam.internal.ResourceConvert;
import pers.ken.rt.iam.internal.Rn;
import pers.ken.rt.iam.utils.PolicyResolver;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code> TestConvert </code>
 * <desc> TestConvert </desc>
 * <b>Creation Time:</b> 2022/8/8 17:40.
 *
 * @author Ken.Hu
 */
public class TestConvert implements ResourceConvert<TestResource> {

    @Override
    public TestResource convert(Rn resource) {
        return null;
    }

    @Override
    public String resourceId() {
        return TestResourceId.TEST;
    }

    @Override
    public SQLExpr sqlCondition(String fieldName) {
        List<TestResource> testResources = PolicyResolver.resolveResource(AuthContext.get().getPolicies(), this);
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

    @Override
    public String tableName() {
        return "test";
    }

    @Override
    public String field() {
        return "id";
    }
}