package pers.ken.rt.pbac.permission.data;

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
    private String resource;
    private String table;
    private String field;
    private DataConditionGenerator generator;

    public DataScopeSetting(String resource, String table, String field) {
        this.table = table;
        this.resource = resource;
        this.field = field;
        this.generator = new DataConditionGenerator() {
        };
    }

    public DataScopeSetting(String resource, String table, String field, DataConditionGenerator generator) {
        this.table = table;
        this.resource = resource;
        this.field = field;
        this.generator = generator;
    }

    public DataScopeSetting() {
    }
}
