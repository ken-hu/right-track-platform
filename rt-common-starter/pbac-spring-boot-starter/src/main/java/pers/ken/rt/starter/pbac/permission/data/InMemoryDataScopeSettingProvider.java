package pers.ken.rt.starter.pbac.permission.data;

import org.apache.commons.lang3.StringUtils;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyContextHolder;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: InMemoryDataProvider
 * @Created: 2024/6/6
 * @Author ken
 */
public class InMemoryDataScopeSettingProvider implements DataScopeSettingProvider {
    private final ConcurrentHashMap<String, DataScopeSetting> settingMap;

    public InMemoryDataScopeSettingProvider(DataScopeSetting... scopeSettings) {
        settingMap = new ConcurrentHashMap<>();
        for (DataScopeSetting scopeSetting : scopeSettings) {
            this.save(scopeSetting);
        }
    }

    @Override
    public void save(DataScopeSetting dataScopeSetting) {
        if (StringUtils.isBlank(dataScopeSetting.getTable())) {
            throw new IllegalArgumentException("table can not be null");
        }
        settingMap.put(dataScopeSetting.getTable(), dataScopeSetting);
    }

    @Override
    public DataPermission toDataPermissions(String table, Set<String> ownResources) {
        DataScopeSetting dataScopeSetting = settingMap.get(table);

        PolicyContext context = PolicyContextHolder.get();
        List<PolicyDocument> policies = context.getPolicies();

//        List<DataPermission.DataCondition> conditions = dataScopeSettings.stream().map(
//                scopeSetting -> DataPermission.DataCondition.builder()
//                        .table(scopeSetting.getTable())
//                        .field(scopeSetting.getField())
//                        .generator(scopeSetting.getConditionGenerator())
//                        .build()
//        ).toList();

        return DataPermission.builder()
                .resourceId(table)
                .ownResources(ownResources)
//                .conditions(conditions)
                .build();
    }


}
