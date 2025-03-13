package pers.ken.rt.starter.pbac.permission.data;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGASTVisitorAdapter;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <code> DataFilterVisitor </code>
 * <desc> SQL递归AVT树重写 </desc>
 * <b>Creation Time:</b> 11/29/2021 11:52 AM.
 *
 * @author _Ken.Hu
 */
public class PgDataScopeVisitor extends PGASTVisitorAdapter {
    private final List<DataPermission> dataScopeSettings;

    public PgDataScopeVisitor(List<DataPermission> dataScopeSettings) {
        this.dataScopeSettings = dataScopeSettings;
    }


    /**
     * 获取表名信息
     *
     * @param sqlExprTableSource x
     * @return boolean
     */
    @Override
    public boolean visit(SQLExprTableSource sqlExprTableSource) {
        for (DataPermission dataScopeSetting : this.dataScopeSettings) {
            String tableSourceName = sqlExprTableSource.getName().getSimpleName();
            // 兼容性处理
            String standardizationTableName = compatibilityProcessing(tableSourceName);

            // MATCH TARGET TABLE
            if (standardizationTableName.equalsIgnoreCase(dataScopeSetting.getTable())) {
                SQLObject parent = sqlExprTableSource.getParent();
                String tableAlias = sqlExprTableSource.getAlias();
                while (!(parent instanceof PGSelectQueryBlock) && parent != null) {
                    parent = parent.getParent();
                }

                /* 插入行控制条件 */
                if (parent != null) {
                    PGSelectQueryBlock query = ((PGSelectQueryBlock) parent);
                    List<SQLExpr> sqlExprs = generateWhereConditions(tableAlias, dataScopeSetting);
                    for (SQLExpr sqlExpr : sqlExprs) {
                        query.addCondition(sqlExpr);
                    }
                }
            }
        }
        return super.visit(sqlExprTableSource);
    }

    /**
     * 兼容部分SQL的语法问题
     *
     * @param tableSourceName
     * @return
     */
    private String compatibilityProcessing(String tableSourceName) {
        return tableSourceName
            .replace("\"", "")
            .replace("'", "")
            .replace("`", "");
    }

    @Override
    public boolean visit(PGSelectQueryBlock x) {
        return super.visit(x);
    }

    /**
     * 适合做列权限操作
     *
     * @param x x
     * @return boolean
     */
    @Override
    public boolean visit(PGSelectStatement x) {
        return super.visit(x);
    }

    @Override
    public void endVisit(SQLInListExpr x) {
        super.endVisit(x);
    }

    private List<SQLExpr> generateWhereConditions(String tableAlias, DataPermission setting) {
        List<DataPermission.DataFilterCondition> dataFilterConditions = setting.getDataFilterConditions();
        return dataFilterConditions.stream().map(cond -> {
            String field = cond.getField();
            if (StringUtils.hasText(tableAlias)) {
                field = tableAlias.concat(".").concat(field);
            } else {
                field = setting.getTable().concat(".").concat(field);
            }
            DataConditionGenerator generator = cond.getConditionGenerator();
            return generator.generateSqlCondition(field, cond.getValues());
        }).toList();
    }

}
