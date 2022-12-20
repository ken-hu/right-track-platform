package pers.ken.rt.test.iam;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import pers.ken.rt.iam.internal.AuthContext;
import pers.ken.rt.iam.internal.ResourceConvert;
import pers.ken.rt.iam.internal.Rn;
import pers.ken.rt.iam.internal.RnResource;
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
public class CategoryConvert implements ResourceConvert<CategoryResource> {

    @Override
    public CategoryResource convert(Rn resource) {
        RnResource rnResource = resource.getRnResource();
        String[] r = rnResource.getResource().split("/");
        return new CategoryResource(r[0], r[2]);
    }

    @Override
    public String resourceId() {
        return TestResourceId.CATEGORY;
    }


    @Override
    public SQLExpr sqlCondition(String fieldName) {
        List<CategoryResource> resources = PolicyResolver.resolveResource(AuthContext.get().getPolicies(), this);
        // 去重
        List<SQLExpr> values = resources.stream()
                .map(CategoryResource::getIcCode)
                .distinct()
                .map(SQLCharExpr::new)
                .collect(Collectors.toList());
        SQLInListExpr sqlInListExpr = new SQLInListExpr();
        sqlInListExpr.setTargetList(Collections.unmodifiableList(values));
        return sqlInListExpr;
    }

    @Override
    public String tableName() {
        return "dict_category";
    }

    @Override
    public String field() {
        return "ic_code";
    }
}