package pers.ken.rt.common.iam.data;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGASTVisitorAdapter;

import java.util.List;

/**
 * <code> DataFilterVisitor </code>
 * <desc> DataFilterVisitor </desc>
 * <b>Creation Time:</b> 11/29/2021 11:52 AM.
 *
 * @author _Ken.Hu
 */
public class PgFilterVisitor extends PGASTVisitorAdapter {

    private final List<DataScope> dataScopes;
    private DataScope dataScope;

    public PgFilterVisitor(List<DataScope> dataScopes) {
        this.dataScopes = dataScopes;
    }


    /**
     * 获取表名信息
     *
     * @param x x
     * @return boolean
     */
    @Override
    public boolean visit(SQLExprTableSource x) {
        if (isTargetTableSource(x, dataScopes)) {
            SQLObject parent = x.getParent();
            String alias = x.getAlias();
            while (!(parent instanceof PGSelectQueryBlock) && parent != null) {
                parent = parent.getParent();
            }

            /* 插入行控制条件 */
            if (parent != null) {
                PGSelectQueryBlock query = ((PGSelectQueryBlock) parent);
                query.addCondition(formCondition(alias));
            }
        }

        return super.visit(x);
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


    /**
     * 是否需要控制的目标表
     *
     * @param tableSource 表源
     * @param dataScopes  数据范围
     * @return boolean
     */
    private boolean isTargetTableSource(SQLExprTableSource tableSource, List<DataScope> dataScopes) {
        for (DataScope target : dataScopes) {
            String standardizationTableName = tableSource.getName().getSimpleName().replace("\"", "");
            standardizationTableName = standardizationTableName.replace("'", "");
            if (standardizationTableName.equalsIgnoreCase(target.getTable())) {
                dataScope = target;
                return true;
            }
        }
        return false;
    }

    private String formCondition(String alias) {
//        return dataScope.getCondition().stream().map(condition -> alias == null ?
//                String.format("%s %s", condition.getField(), condition.getValues()) :
//                String.format("%s %s", alias + "." + condition.getField(), condition.getValues())).collect(Collectors.joining(" and "));
        return null;
    }
}
