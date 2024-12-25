package pers.ken.rt.starter.pbac.permission.data;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGASTVisitorAdapter;

import java.util.List;

/**
 * <code> DataFilterVisitor </code>
 * <desc> SQL递归AVT树重写 </desc>
 * <b>Creation Time:</b> 11/29/2021 11:52 AM.
 *
 * @author _Ken.Hu
 */
public class PgDataFilterVisitor extends PGASTVisitorAdapter {
    private final List<DataPermission> dataPermissions;

    public PgDataFilterVisitor(List<DataPermission> dataPermissions) {
        this.dataPermissions = dataPermissions;
    }


    /**
     * 获取表名信息
     *
     * @param sqlExprTableSource x
     * @return boolean
     */
    @Override
    public boolean visit(SQLExprTableSource sqlExprTableSource) {
        for (DataPermission dataPermission : this.dataPermissions) {
            for (DataPermission.DataCondition condition : dataPermission.getConditions()) {
                String tableSourceName = sqlExprTableSource.getName().getSimpleName();
                // 兼容性处理
                String standardizationTableName = compatibilityProcessing(tableSourceName);

                // MATCH TARGET TABLE
                if (standardizationTableName.equalsIgnoreCase(condition.getTable())) {
                    SQLObject parent = sqlExprTableSource.getParent();
                    String alias = sqlExprTableSource.getAlias();
                    while (!(parent instanceof PGSelectQueryBlock) && parent != null) {
                        parent = parent.getParent();
                    }

                    /* 插入行控制条件 */
                    if (parent != null) {
                        PGSelectQueryBlock query = ((PGSelectQueryBlock) parent);
                        query.addCondition(whereCondition(alias, dataPermission, condition));
                    }
                }
            }
        }
        return super.visit(sqlExprTableSource);
    }

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

    private SQLExpr whereCondition(String tableAlias, DataPermission dataPermission, DataPermission.DataCondition condition) {
        return condition.getGenerator().generateSqlCondition(tableAlias, condition.getField(), dataPermission.getOwnResources());
    }

}
