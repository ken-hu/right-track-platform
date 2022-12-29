package pers.ken.rt.iam.permission.data;

import com.alibaba.druid.sql.ast.SQLExpr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <code> DataScope </code>
 * <desc> DataScope </desc>
 * <b>Creation Time:</b> 2022/8/25 17:51.
 *
 * @author Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataScope {
    private String table;
    private SQLExpr sqlCondition;
}
