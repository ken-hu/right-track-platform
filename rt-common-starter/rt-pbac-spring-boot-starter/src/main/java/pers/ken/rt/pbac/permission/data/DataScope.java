package pers.ken.rt.pbac.permission.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * The type Data scope.
 *
 * @ClassName: InMemoryDataProvider
 * @CreatedTime: 2023 /1/11 14:53
 * @Desc: Just like : resource city and bind table dim_city and map_data Then {"resource":"city","dataConditions":[{"table":"dim_city","field":"adcode","sqlCondition":"where city_code in ('400100')"},{"table":"map_data","field":"city_code","sqlCondition":"where adcode in ('400100')"}]}
 * @Author Ken
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataScope {
    /**
     * 绑定的资源
     */
    private String resourceId;
    private Set<String> ownResources;
    private List<DataCondition> conditions;

    /**
     * 数据条件
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataCondition {
        /**
         * 绑定的Table
         */
        private String table;
        /**
         * 绑定的字段
         */
        private String field;
        /**
         * 行权限的SQL条件生成器
         */
        private DataConditionGenerator generator;

    }
}
