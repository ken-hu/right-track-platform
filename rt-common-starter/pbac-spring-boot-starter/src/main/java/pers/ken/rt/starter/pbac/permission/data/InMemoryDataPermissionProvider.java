package pers.ken.rt.starter.pbac.permission.data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: InMemoryDataProvider
 * @Created: 2024/6/6
 * @Author ken
 */
public class InMemoryDataPermissionProvider implements DataPermissionProvider {
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, List<String>>> PERMISSION_MAP = new ConcurrentHashMap<>();

    @Override
    public void save(String table, String field, List<String> values) {
        PERMISSION_MAP.computeIfAbsent(table, k -> new ConcurrentHashMap<>())
            .compute(field, (k, existing) -> {
                List<String> newValues = new ArrayList<>(values); // 存储副本
                if (existing == null) {
                    return newValues;
                } else {
                    // 合并策略：覆盖/追加/去重，根据业务需求选择
                    existing.addAll(newValues);
                    return new ArrayList<>(new LinkedHashSet<>(existing)); // 去重示例
                }
            });
    }

    @Override
    public List<String> loadDataPermissionValues(String table, String field) {
        ConcurrentHashMap<String, List<String>> permissionValueMap = PERMISSION_MAP.get(table);
        if (null != permissionValueMap) {
            return permissionValueMap.get(field);
        }
        return null;
    }
}
