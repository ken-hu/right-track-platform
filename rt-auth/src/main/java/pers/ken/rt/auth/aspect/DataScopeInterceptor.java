package pers.ken.rt.auth.aspect;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import pers.ken.rt.starter.pbac.permission.data.DataPermission;
import pers.ken.rt.starter.pbac.permission.data.DataPermissionContextHolder;
import pers.ken.rt.starter.pbac.permission.data.PgDataScopeVisitor;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName: DataScopeInterceptor
 * @Created: 2025/3/11 15:19
 * @Author ken
 * Mybatis自带拦截器 完成 数据权限过滤
 */
@Slf4j
public class DataScopeInterceptor implements InnerInterceptor {
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String sql = mpBs.sql();
        List<DataPermission> dataPermissions = DataPermissionContextHolder.get();
        if (null != dataPermissions) {
            PgDataScopeVisitor visitor = new PgDataScopeVisitor(dataPermissions);
            List<SQLStatement> statements = SQLUtils.parseStatements(sql, DbType.postgresql);
            statements.forEach(statement -> {
                statement.accept(visitor);
            });
            String newSql = statements.get(0).toString();
            mpBs.sql(newSql);
            InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }

    }
}
