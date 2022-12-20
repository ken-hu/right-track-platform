package pers.ken.rt.test.iam;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;
import pers.ken.rt.iam.internal.AuthContext;
import pers.ken.rt.iam.internal.ResourceAccessDetail;
import pers.ken.rt.iam.permission.data.IDataProvider;
import pers.ken.rt.iam.permission.data.PgFilterVisitor;

import java.util.List;
import java.util.Objects;

/**
 * Creation Time: 2022/11/14 14:58.
 *
 * @author _Ken.Hu
 */
@Component
public class DataAccessPlugin implements StatementInspector {

    @Override
    public String inspect(String sql) {
        if (Objects.isNull(AuthContext.get())) {
            return sql;
        }
        ResourceAccessDetail resourceAccessDetail = AuthContext.get();
        List<IDataProvider> dataProviders = resourceAccessDetail.getDataProviders();
        PgFilterVisitor visitor = new PgFilterVisitor(dataProviders);
        List<SQLStatement> statements = SQLUtils.parseStatements(sql, DbType.postgresql);
        statements.forEach(statement -> statement.accept(visitor));
        return statements.get(0).toString();
    }

}
