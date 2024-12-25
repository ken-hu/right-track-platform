package pers.ken.rt.auth.config;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * @ClassName: MybatisPlusDataScopeInterceptor
 * @Created: 2024/12/11 20:58
 * @Author ken
 */
public class MybatisPlusDataScopeInterceptor implements InnerInterceptor {
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String generateSQL = generateSQL(mpBs.sql());
        mpBs.sql(generateSQL);
    }

    private String generateSQL(String originSQL) {
        return null;
    }
}
