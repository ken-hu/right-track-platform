package pers.ken.rt.mall.config;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import pers.ken.rt.common.web.SpringContextHolder;
import pers.ken.rt.pbac.permission.data.DataScope;
import pers.ken.rt.pbac.permission.data.IDataProvider;
import pers.ken.rt.pbac.permission.data.PgDataFilterVisitor;

import java.util.List;

/**
 * @ClassName: DataAccessInterceptor
 * @CreatedTime: 2023/1/16 19:01
 * @Desc:
 * @Author Ken
 */
@Slf4j
public class DataAccessInterceptor implements StatementInspector {
    @Override
    public String inspect(String originalSql) {
        IDataProvider dataProvider = SpringContextHolder.getBean(IDataProvider.class);
        List<DataScope> dataScopes = dataProvider.fromContext();
        PgDataFilterVisitor pgDataFilterVisitor = new PgDataFilterVisitor(dataScopes);
        List<SQLStatement> statements = SQLUtils.parseStatements(originalSql, DbType.postgresql);
        statements.forEach(statement -> {
            statement.accept(pgDataFilterVisitor);
        });
        String newSql = SQLUtils.toSQLString(statements, DbType.postgresql);
        log.info("originalSql:{}", originalSql);
        log.info("newSql:{}", newSql);
        return newSql;
    }
}
