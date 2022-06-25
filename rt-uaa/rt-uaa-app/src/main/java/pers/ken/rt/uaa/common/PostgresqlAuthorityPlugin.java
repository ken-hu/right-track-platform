package pers.ken.rt.uaa.common;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.google.common.collect.Lists;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;
import pers.ken.rt.common.iam.IAMAuthContext;
import pers.ken.rt.common.iam.UserAuthority;
import pers.ken.rt.common.iam.data.DataScope;
import pers.ken.rt.common.iam.data.PgFilterVisitor;

import java.util.List;

/**
 * <code> AuthorityPlugin </code>
 * <desc> AuthorityPlugin </desc>
 * <b>Creation Time:</b> 2022/3/6 22:36.
 *
 * @author _Ken.Hu
 */
@Component
public class PostgresqlAuthorityPlugin implements StatementInspector {

    @Override
    public String inspect(String sql) {
        UserAuthority userAuthority = IAMAuthContext.get();

        PgFilterVisitor PgFilterVisitor = new PgFilterVisitor(Lists.newArrayList(new DataScope()));
        List<SQLStatement> statements = SQLUtils.parseStatements(sql, DbType.postgresql);
        statements.forEach(statement -> statement.accept(PgFilterVisitor));
        return statements.get(0).toString();
    }
}
