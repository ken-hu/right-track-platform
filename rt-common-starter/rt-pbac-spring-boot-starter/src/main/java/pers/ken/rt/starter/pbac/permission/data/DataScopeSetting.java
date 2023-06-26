package pers.ken.rt.starter.pbac.permission.data;

import lombok.Data;
import pers.ken.rt.starter.pbac.anno.DataConditionGenerator;

/**
 * @ClassName: DataScopeSetting
 * @CreatedTime: 2023/1/11 18:11
 * @Desc:
 * @Author Ken
 */
@Data
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

}
