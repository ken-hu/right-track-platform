package pers.ken.rt.starter.pbac.permission.data;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * The interface Data condition generator.
 *
 * @ClassName: DataConditionGenerator
 * @CreatedTime: 2023 /1/11 16:38
 * @Desc:
 * @Author Ken
 */
public interface DataConditionGenerator {
    /**
     * Generate where condition sql expr.
     * 默认采用SQL where filed in (x,x,x) 做包含查询
     *
     * @param field      the field
     * @param values     the ownResources
     * @return the sql expr
     */
    default SQLExpr generateSqlCondition(String field, List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        List<SQLExpr> sqlInValues = values
            .stream()
            .distinct()
            .map(SQLCharExpr::new)
            .map(x -> (SQLExpr) x)
            .toList();
        SQLInListExpr sqlInListExpr = new SQLInListExpr(field);
        sqlInListExpr.setTargetList(sqlInValues);
        return sqlInListExpr;
    }

}
