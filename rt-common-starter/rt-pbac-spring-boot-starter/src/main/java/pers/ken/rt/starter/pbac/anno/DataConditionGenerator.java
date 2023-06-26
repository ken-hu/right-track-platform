package pers.ken.rt.starter.pbac.anno;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pers.ken.rt.starter.pbac.internal.Rn;

import java.util.List;
import java.util.Set;

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
     *
     * @param tableAlias   the tableAlias
     * @param field        the field
     * @param ownResources the ownResources
     * @return the sql expr
     */
    default SQLExpr generateWhereCondition(String tableAlias, String field, Set<String> ownResources) {
        if (CollectionUtils.isEmpty(ownResources)) {
            return null;
        }
        if (StringUtils.hasText(tableAlias)) {
            field = tableAlias.concat(".").concat(field);
        }
        List<SQLExpr> values = ownResources.stream().map(
                        r -> {
                            Rn rn = Rn.fromString(r);
                            return rn.getRnResource().getResource();
                        })
                .distinct()
                .map(SQLCharExpr::new)
                .map(x -> (SQLExpr) x)
                .toList();
        SQLInListExpr sqlInListExpr = new SQLInListExpr(field);
        sqlInListExpr.setTargetList(values);
        return sqlInListExpr;
    }

}
