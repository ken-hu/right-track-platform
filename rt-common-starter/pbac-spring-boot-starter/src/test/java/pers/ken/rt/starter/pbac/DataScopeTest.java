package pers.ken.rt.starter.pbac;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pers.ken.rt.starter.pbac.annotation.DataCondition;
import pers.ken.rt.starter.pbac.permission.data.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DataScopeTest
 * @Created: 2025/3/11 17:14
 * @Author ken
 */
public class DataScopeTest {

    @Test
    @DisplayName("SQL权限转换逻辑梳理")
    void sqlTestLogic() {
        // 测试数据
        // 单表+别名
        String sql = """
            select * from t_users as user;
            """;

        // 注解拦截， 注解读取到 table field conditionGenerator
        // 通过table+field 找到 dataPermission (Provider去load)
        // 通过构建DataPermission 注入到 PgFilterVisitor
        DataPermissionProvider dataPermissionProvider = new InMemoryDataPermissionProvider();
        dataPermissionProvider.save("t_users", "id", List.of("440100", "234"));
        DataPermission dataPermission = dataPermissionProvider.loadDataPermission("t_users", new DataCondition[]{
            new DataCondition() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return DataCondition.class;
                }

                @Override
                public String value() {
                    return "id";
                }

                @Override
                public Class<? extends DataConditionGenerator> conditionGenerator() {
                    return WhereInConditionGenerator.class;
                }
            }
        });

        ArrayList<DataPermission> settings = new ArrayList<>();
        settings.add(dataPermission);
        sqlGenerate(settings, sql);
    }


    @Test
    @DisplayName("SQL权限转换测试1")
    void sqlTest1() {
        // 测试数据
        // 单表+别名
        String sql = """
            select * from t_users as user;
            """;

        DataPermission setting = DataPermission.of("t_users", "id", Lists.newArrayList("1", "2"), new DataConditionGenerator() {
        });

        ArrayList<DataPermission> settings = new ArrayList<>();
        settings.add(setting);
        sqlGenerate(settings, sql);
    }


    @Test
    @DisplayName("SQL权限转换测试2")
    void sqlTest2() {
        //TODO 字段的别名测试有问题

        // 测试数据
        // 单表+别名
        String sql = """
            select _tmp_auto_area_points.id,
                   _tmp_auto_area_points.name,
                   _tmp_auto_area_points.address,
                   _tmp_auto_area_points.center_loc,
                   _tmp_auto_area_points.level2_cn,
                   _tmp_auto_area_points.level3_cn,
                   _tmp_auto_area_points.level4_cn,
                   _tmp_auto_area_points.ic_code,
                   _tmp_auto_area_points.attribute,
                   _tmp_auto_area_points.shop_type,
                   _tmp_auto_area_points.origin_shop_type,
                   _tmp_auto_area_points.cid,
                   dict_adcode.province_cn,
                   dict_adcode.province_code,
                   dict_adcode.city_cn,
                   dict_adcode.city_code,
                   dict_adcode.name as adname,
                   dict_adcode.adcode
            from _tmp_auto_area_points
                    left join dict_adcode on _tmp_auto_area_points.adcode = dict_adcode.adcode
                and dict_adcode.adlevel = 'DISTRICT';
            """;

        DataPermission setting = DataPermission.of("dict_adcode", "city_code", Lists.newArrayList("440100", "500100"), new DataConditionGenerator() {
        });
        ArrayList<DataPermission> settings = new ArrayList<>();
        settings.add(setting);
        sqlGenerate(settings, sql);
    }

    @Test
    @DisplayName("SQL权限转换测试3")
    void sqlTest3() {
        //TODO 字段的别名测试有问题

        // 测试数据
        // 单表+别名
        String sql = """
            with shopping as (select *
                               from map_aoi
                               where data_type = 'mall_aoi_ca'
                                 and enterprise_id = 3589
                                 and is_deleted = false),
                  shopping_poi as (select mpo.id
                                   from map_poi mpo
                                            inner join shopping
                                                       on st_contains(shopping.geom, mpo.center_loc)
                                   where 1 = 1
                                     and mpo.enterprise_id = 3589
                                     and mpo.ic_code in(
                                         select ic_code from dict_industry_category where level1_code = '11111111'
                                     ))
             select *
             from shopping_poi;
            """;

        DataPermission setting = DataPermission.of("dict_industry_category", "ic_code", Lists.newArrayList("440100", "500100"), new DataConditionGenerator() {
        });
        ArrayList<DataPermission> settings = new ArrayList<>();
        settings.add(setting);
        sqlGenerate(settings, sql);
    }

    private void sqlGenerate(List<DataPermission> settings, String sql) {
        // 运行
        PgDataScopeVisitor visitor = new PgDataScopeVisitor(settings);
        List<SQLStatement> statements = SQLUtils.parseStatements(sql, DbType.postgresql);
        statements.forEach(statement -> {
            statement.accept(visitor);
        });
        String newSQL = statements.get(0).toString();
        System.out.printf("""
            NEW SQL:
            ------------
            %s
            ------------
            """, newSQL);
    }


    // 1. 新场景 旧方法 dept    DataRule -> List<String> -> values -> Where in ()
    // 2. 新/旧场景的 新方法  (没做) DataConditionGenerator： select ic_code from dict_industry_category where level1_code = '11111111'
    //    DATA_PERMISSION_TABLE
    //    type  identity  resource/table resource resource_val
    //    dept  id1       industry_category  level1_code 11111111
}
