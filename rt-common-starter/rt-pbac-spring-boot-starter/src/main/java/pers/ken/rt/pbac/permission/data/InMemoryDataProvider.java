package pers.ken.rt.pbac.permission.data;

import com.google.common.collect.Sets;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pers.ken.rt.pbac.internal.AuthContextHolder;
import pers.ken.rt.pbac.internal.Policy;
import pers.ken.rt.pbac.internal.PolicyContext;
import pers.ken.rt.pbac.utils.PolicyResolver;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName: InMemoryDataProvider
 * @CreatedTime: 2023/1/11 14:53
 * @Desc:
 * @Author Ken
 */
public class InMemoryDataProvider implements IDataProvider {
    private final Map<String, Set<DataScopeSetting>> dataScopeSettingMap;

    public InMemoryDataProvider(List<DataScopeSetting> dataScopeSettings) {
        Assert.notEmpty(dataScopeSettings, "DataScopeSettings cannot be empty");
        this.dataScopeSettingMap = new ConcurrentHashMap<>();
        for (DataScopeSetting setting : dataScopeSettings) {
            defineDataScopeSetting(setting);
        }
    }

    public InMemoryDataProvider() {
        this.dataScopeSettingMap = new ConcurrentHashMap<>();
    }

    @Override
    public void defineDataScopeSetting(DataScopeSetting setting) {
        if (StringUtils.hasText(setting.getResource())) {
            String resource = setting.getResource();
            if (Objects.nonNull(dataScopeSettingMap.get(setting.getResource()))) {
                dataScopeSettingMap.get(resource).add(setting);
            } else {
                Sets.newHashSet(setting);
                dataScopeSettingMap.put(setting.getResource(), Sets.newHashSet(setting));
            }
        }
    }

    @Override
    public List<DataScope> fromContext() {
        PolicyContext policyContext = AuthContextHolder.get();
        List<Policy> policies = policyContext.getPolicies();
        Map<String, String> accessResources = policyContext.getAccessResources();
        return accessResources.keySet().stream()
                .map(s -> {
                    Set<String> policyOwnResources = PolicyResolver.getPolicyOwnResources(policies, s);
                    return buildDataScope(s, policyOwnResources);
                }).collect(Collectors.toList());
    }

    @Override
    public DataScope generateDataScope(List<Policy> policies, String resourceId) {
        Set<String> policyOwnResources = PolicyResolver.getPolicyOwnResources(policies, resourceId);
        return buildDataScope(resourceId, policyOwnResources);
    }

    private DataScope buildDataScope(String resourceId, Set<String> ownResources) {
        Set<DataScopeSetting> dataScopeSettings = dataScopeSettingMap.get(resourceId);
        List<DataScope.DataCondition> conditions = dataScopeSettings.stream()
                .map(setting -> new DataScope.DataCondition(setting.getTable(), setting.getField(), setting.getGenerator())).collect(Collectors.toList());
        return new DataScope(resourceId, ownResources, conditions);
    }
}
