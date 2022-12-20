package pers.ken.rt.iam.permission.data;

import com.alibaba.druid.sql.ast.SQLExpr;

/**
 * <code> IDataProvider </code>
 * <desc> IDataProvider </desc>
 * <b>Creation Time:</b> 2022/8/9 14:31.
 *
 * @author Ken.Hu
 */
public interface IDataProvider {

    /**
     * Sql condition sql expr.
     *
     * @return the sql expr
     */
    SQLExpr sqlCondition(String fieldName);

    /**
     * Table name string.
     *
     * @return the string
     */
    String tableName();

    /**
     * Field string.
     *
     * @return the string
     */
    String field();
}
