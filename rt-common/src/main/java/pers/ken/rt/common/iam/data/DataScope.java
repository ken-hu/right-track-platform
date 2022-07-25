package pers.ken.rt.common.iam.data;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import lombok.Data;

import java.util.List;

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
    private List<DataCondition> conditions;

    public static class DataCondition {
        private DataRule leftNode;
        private Operate operate;
        private DataRule rightNode;
    }

    public static class DataRule {
        private String field;
        private Operate operate;
        private String value;
    }

    public static SQLExpr toSqlExpr(DataScope dataScope) {
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
        eq,
        like,
        gte,
        lte,
        gt,
        lt
    }
}
