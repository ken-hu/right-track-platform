package pers.ken.rt.starter.pbac.permission.data;

import org.springframework.beans.BeanUtils;
import pers.ken.rt.starter.pbac.annotation.DataCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * <code> IDataProvider </code>
 * <desc> 数据权限提供 </desc>
 * <b>Creation Time:</b> 2022/8/9 14:31.
 *
 * @author Ken.Hu
 */
public interface DataPermissionProvider {
    /**
     * Save.
     *
     * @param table  the table
     * @param field  the field
     * @param values the values
     */
    default void save(String table, String field, List<String> values) {
    }

    ;


    /**
     * Load data permission data permission.
     *
     * @param table the table
     * @param field the field
     * @return the data permission
     */
    List<String> loadDataPermissionValues(String table, String field);


    default DataPermission loadDataPermission(String table, DataCondition[] conditions) {
        ArrayList<DataPermission.DataFilterCondition> dataFilterConditions = new ArrayList<>();
        for (DataCondition condition : conditions) {
            Class<? extends DataConditionGenerator> clazz = condition.conditionGenerator();
            DataConditionGenerator generator = BeanUtils.instantiateClass(clazz);
            List<String> values = loadDataPermissionValues(table, condition.value());
            DataPermission.DataFilterCondition dataFilterCondition = DataPermission.DataFilterCondition.builder()
                .field(condition.value())
                .values(values)
                .conditionGenerator(generator)
                .build();
            dataFilterConditions.add(dataFilterCondition);
        }
        return DataPermission.builder()
            .table(table)
            .dataFilterConditions(dataFilterConditions)
            .build();
    }
}
