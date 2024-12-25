package pers.ken.rt.starter.pbac.permission.data;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: DataScopeSetting
 * @CreatedTime: 2023/1/11 18:11
 * @Desc:
 * @Author Ken
 */
@Data
@Builder
public class DataScopeSetting {
    private String table;
    private String field;
    @Builder.Default
    private DataConditionGenerator conditionGenerator = new DataConditionGenerator() {
    };
}
