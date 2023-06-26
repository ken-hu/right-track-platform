package pers.ken.rt.starter.pbac.permission.data;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGASTVisitorAdapter;
import pers.ken.rt.starter.pbac.anno.IDataProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <code> DataFilterVisitor </code>
 * <desc> DataFilterVisitor </desc>
 * <b>Creation Time:</b> 11/29/2021 11:52 AM.
 *
 * @author _Ken.Hu
 */
public class PgDataFilterVisitor extends PGASTVisitorAdapter {
    private final List<DataScope> dataScopes;

    public PgDataFilterVisitor(IDataProvider dataProvider) {
        Collection<DataScope> dataScopes = dataProvider.fromContext();
        this.dataScopes = new ArrayList<>(dataScopes);
    }

    public PgDataFilterVisitor(List<DataScope> dataScopes) {
        this.dataScopes = dataScopes;
    }


    /**
     * 获取表名信息
     *
     * @param sqlExprTableSource x
     * @return boolean
     */
    @Override
    public boolean visit(SQLExprTableSource sqlExprTableSource) {
        for (DataScope dataScope : this.dataScopes) {
            for (DataScope.DataCondition condition : dataScope.getConditions()) {
                String tableSourceName = sqlExprTableSource.getName().getSimpleName();
                String standardizationTableName = tableSourceName
                        .replace("\"", "")
                        .replace("'", "")
                        .replace("`", "");
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
                        query.addCondition(whereCondition(alias, dataScope, condition));
                    }
                }
            }
        }
        return super.visit(sqlExprTableSource);
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

    private SQLExpr whereCondition(String tableAlias, DataScope dataScope, DataScope.DataCondition condition) {
        return condition.getGenerator().generateWhereCondition(tableAlias, condition.getField(), dataScope.getOwnResources());
    }

}
