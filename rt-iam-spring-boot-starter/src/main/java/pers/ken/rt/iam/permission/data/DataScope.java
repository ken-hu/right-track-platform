package pers.ken.rt.iam.permission.data;

import com.alibaba.druid.sql.ast.SQLExpr;
import lombok.Data;

/**
 * <code> DataScope </code>
 * <desc> DataScope </desc>
 * <b>Creation Time:</b> 2022/8/25 17:51.
 *
 * @author Ken.Hu
 */
@Data
public class DataScope {
    private String table;
    private SQLExpr sqlCondition;
}
