package pers.ken.rt.test.iam;


import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <code> TestDataProviderTest </code>
 * <desc> TestDataProviderTest </desc>
 * <b>Creation Time:</b> 2022/8/25 14:18.
 *
 * @author Ken.Hu
 */
class TestDataProviderTest {

    @Test
    public void test(){
        List<String> collect = Stream.of("1", "2", "3").collect(Collectors.toList());
//        List<Policy> policies = new ArrayList<>();
//        List<TestResource> testResources = PolicyResolver.resolveResource(policies, new TestConvert());
        // 去重
        List<SQLExpr> values = collect.stream()
                .distinct()
                .map(SQLCharExpr::new)
                .collect(Collectors.toList());
        System.out.println(values);
        SQLIdentifierExpr sqlIdentifierExpr = new SQLIdentifierExpr("name");
        SQLInListExpr sqlInListExpr = new SQLInListExpr(sqlIdentifierExpr);
        sqlInListExpr.setTargetList(values);
        System.out.println(sqlInListExpr);
    }
}