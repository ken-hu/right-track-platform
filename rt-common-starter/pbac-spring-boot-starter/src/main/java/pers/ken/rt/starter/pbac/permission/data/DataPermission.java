package pers.ken.rt.starter.pbac.permission.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: DataFilterCondition
 * @Created: 2025/3/12 11:06
 * @Author ken
 */
@Data
@Builder
public class DataPermission {
    private String table;
    private List<DataFilterCondition> dataFilterConditions;

    @Data
    @Builder
    public static class DataFilterCondition {
        private String field;
        private List<String> values;
        @Builder.Default
        private DataConditionGenerator conditionGenerator = new DataConditionGenerator() {
        };
    }

    public static DataPermission of(String table, String field, List<String> values, DataConditionGenerator conditionGenerator) {
        return DataPermission.builder()
            .table(table)
            .dataFilterConditions(List.of(new DataFilterCondition(field, values, conditionGenerator)))
            .build();
    }

}
