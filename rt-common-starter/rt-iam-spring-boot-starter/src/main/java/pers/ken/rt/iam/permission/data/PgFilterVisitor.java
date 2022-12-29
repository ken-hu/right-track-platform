package pers.ken.rt.iam.permission.data;

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
 * <desc> DataFilterVisitor </desc>
 * <b>Creation Time:</b> 11/29/2021 11:52 AM.
 *
 * @author _Ken.Hu
 */
public class PgFilterVisitor extends PGASTVisitorAdapter {
    private final List<IDataProvider> dataProviders;

    public PgFilterVisitor(List<IDataProvider> dataProviders) {
        this.dataProviders = dataProviders;
    }

    /**
     * 获取表名信息
     *
     * @param sqlExprTableSource x
     * @return boolean
     */
    @Override
    public boolean visit(SQLExprTableSource sqlExprTableSource) {
        for (IDataProvider provider : this.dataProviders) {
            String standardizationTableName = sqlExprTableSource.getName().getSimpleName().replace("\"", "");
            standardizationTableName = standardizationTableName.replace("'", "");
            // MATCH TARGET TABLE
            if (standardizationTableName.equalsIgnoreCase(provider.tableName())) {
                SQLObject parent = sqlExprTableSource.getParent();
                String alias = sqlExprTableSource.getAlias();
                while (!(parent instanceof PGSelectQueryBlock) && parent != null) {
                    parent = parent.getParent();
                }

                /* 插入行控制条件 */
                if (parent != null) {
                    PGSelectQueryBlock query = ((PGSelectQueryBlock) parent);
                    query.addCondition(formCondition(alias, provider));
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

    private SQLExpr formCondition(String tableAlias, IDataProvider provider) {

        String field = provider.field();

        if (StringUtils.hasText(tableAlias)) {
            field = tableAlias.concat(".").concat(field);
        }
        return provider.sqlCondition(field);
    }
}
