package pers.ken.rt.starter.pbac.permission.data;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;
import lombok.Getter;
import pers.ken.rt.starter.pbac.internal.PolicyContext;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataScopeContext
 * @Created: 2023-11-16 15:58:51
 * @Description:
 * @Author ken
 */
@Getter
public class DataScopeContext {
    private final List<DataPermission> dataPermissions;

    private DataScopeContext(List<DataPermission> dataPermissions) {
        this.dataPermissions = dataPermissions;
    }

    public static DataScopeContext init(PolicyContext policyContext, Map<String, DataScopeSetting> dataScopeSettingMap) {
//        List<DataScope> scopes = new ArrayList<>();
//        List<PolicyDocument> policies = policyContext.getPolicies();
//        for (PolicyDocument policy : policies) {
//            for (Statement statement : policy.getStatements()) {
//                Effect effect = statement.getEffect();
//                List<String> resources = statement.getResources();
//                resources.forEach(resource -> {
//
//                });
//            }
//        }
//        return new DataScopeContext(scopes);
        return null;
    }

    public String rewiteSQL(String originalSql, SQLASTVisitor sqlastVisitor) {
        List<SQLStatement> statements = SQLUtils.parseStatements(originalSql, DbType.postgresql);
        statements.forEach(statement -> statement.accept(sqlastVisitor));
        return SQLUtils.toSQLString(statements, DbType.postgresql);
    }
}
