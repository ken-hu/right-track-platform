package pers.ken.rt.mall.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

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
//        IDataProvider dataProvider = SpringContextHolder.getBean(IDataProvider.class);
//        List<DataScope> dataScopes = dataProvider.fromContext();
//        PgDataFilterVisitor pgDataFilterVisitor = new PgDataFilterVisitor(dataScopes);
//        List<SQLStatement> statements = SQLUtils.parseStatements(originalSql, DbType.postgresql);
//        statements.forEach(statement -> {
//            statement.accept(pgDataFilterVisitor);
//        });
//        String newSql = SQLUtils.toSQLString(statements, DbType.postgresql);
//        log.info("originalSql:{}", originalSql);
//        log.info("newSql:{}", newSql);
        return originalSql;
    }
}
