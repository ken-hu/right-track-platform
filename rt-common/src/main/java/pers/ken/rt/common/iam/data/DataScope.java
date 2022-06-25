package pers.ken.rt.common.iam.data;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import lombok.Data;

/**
 * <code> DatadRule </code>
 * <desc> DatadRule </desc>
 * <b>Creation Time:</b> 2022/2/12 18:16.
 *
 * @author _Ken.Hu
 */
@Data
public class DataScope {
    private String table;
    private DataRule rule;

    public static class DataRule {
        private String field;
        private String operate;
        private String value;
    }

    public static SQLExpr toSqlExpr(DataScope dataScope){
        //todo
        String s = dataScope.toString();
        return SQLUtils.toSQLExpr(s);
    }

    public enum Operate {
        /**
         * data operate
         */
        or,
        and,
        in,
        equals,
        like
    }
}
