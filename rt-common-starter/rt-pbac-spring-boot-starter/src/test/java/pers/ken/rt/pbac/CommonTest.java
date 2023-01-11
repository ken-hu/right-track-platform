package pers.ken.rt.pbac;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import pers.ken.rt.pbac.internal.Policy;
import pers.ken.rt.pbac.internal.Rn;
import pers.ken.rt.pbac.permission.data.*;
import pers.ken.rt.pbac.utils.Jackson;
import pers.ken.rt.pbac.utils.PolicyResolver;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName: CommonTest
 * @CreatedTime: 2023/1/10 18:16
 * @Desc:
 * @Author Ken
 */
public class CommonTest {

    @Test
    void rnConvertTest() {
        String rnStr = "rt:map:category:1";
        Rn rn = Rn.fromString(rnStr);
        System.out.println(Jackson.toJsonString(rn));
    }

    @Test
    void testJsonNode() {
        // what you can get
        // aop : actionId method value
        // id: spel

        String actionId = "listDimAmapConf";
        String resource = "popType:1";
        String resource2 = "popType:2";

        String policiesJson = """
                {
                  "id": "null",
                  "version": "1.0",
                  "statements": [
                    {
                      "id": null,
                      "actions": [
                        "listDimAmapConf",
                        "mars:GetMapDimAmapConfUserAmapInfo",
                        "mars:GetMapAoiBusinessCategoryList",
                        "mars:PostMapProvinceCategoryRanking",
                        "mars:GetMapProvinceCategoryIndicator"
                      ],
                      "effect": "Allow",
                      "resources": [
                        "channel:mars:evaluateDataSource:1",
                        "channel:mars:businessCircle:*",
                        "popType:1",
                        "popType:2",
                        "popType:3",
                        "channel:mars:heatType:*",
                        "channel:mars:city:${adcode}",
                        "channel:mars:poi:*/*",
                        "channel:mars:industryCategory:*",
                        "channel:mars:contrastAnalysisSelector:*",
                        "desc/*"
                      ]
                    }
                  ]
                }
                """;

        String originalSql = """
                WITH t_adcode AS (SELECT adcode
                                  FROM dict_adcode
                                  WHERE adlevel = 'DISTRICT'
                                    AND city_code = 440100),
                     t_category AS (SELECT ic_code,
                                           parent_ic_code
                                    FROM dict_industry_category),
                     t_poi AS (SELECT id,
                                      ic_code,
                                      city_code,
                                      city_id,
                                      adcode,
                                      center_loc,
                                      attribute
                               FROM map_poi
                               WHERE  city_id ='1003638925920000049'
                                 AND (map_poi.ic_code = 101010000000
                                   OR map_poi.ic_code = 151510000039
                                   OR map_poi.ic_code = 151510000139)),
                     t_aoi AS (SELECT *
                               FROM map_aoi
                               WHERE city_id ='1003638925920000049'
                                 AND (data_type IN ('street_2022_v2', 'road_cut_street_v2', 'live_area')
                                   AND enterprise_id = 78)
                                 AND (attribute ->> 'pharmacy_bus_heatval')::decimal(10, 2) > 70.0)
                SELECT t_poi.ic_code,
                       count(t_poi.id) AS cnt,
                       t_aoi.id,
                       t_aoi.attribute,
                       t_aoi.name,
                       t_aoi.data_type,
                       t_aoi.adcode,
                       t_category.parent_ic_code
                FROM t_aoi
                         JOIN
                     t_adcode
                     ON t_adcode.adcode = t_aoi.adcode
                         JOIN
                     t_poi
                     ON t_aoi.city_id = t_poi.city_id
                         AND st_contains(t_aoi.geom,
                                         t_poi.center_loc)
                         JOIN
                     t_category
                     ON t_poi.ic_code = t_category.ic_code
                         AND t_category.ic_code IN (
                                                    101010000000,
                                                    151510000039,
                                                    151510000139)
                GROUP BY t_poi.ic_code,
                         t_aoi.id,
                         t_aoi.attribute,
                         t_aoi.name,
                         t_aoi.data_type,
                         t_aoi.adcode,
                         t_category.parent_ic_code;
                """;
        Policy policy = Jackson.fromJsonString(policiesJson, new TypeReference<Policy>() {
        });

        List<Policy> policies = Collections.singletonList(policy);
        boolean permit = PolicyResolver.isPermit(policies,
                Lists.newArrayList(resource, resource2),
                actionId);

        System.out.println(permit);

        InMemoryDataProvider inMemoryDataProvider = new InMemoryDataProvider();
        DataScopeSetting setting = new DataScopeSetting("map_poi", "popType", "ic_code", new DataConditionGenerator() {
        });
        inMemoryDataProvider.defineDataScopeSetting(setting);
        DataScope dataScope = inMemoryDataProvider.generateDataScope(policies, "popType");
        PgDataFilterVisitor pgDataFilterVisitor = new PgDataFilterVisitor(Lists.newArrayList(dataScope));
        List<SQLStatement> statements = SQLUtils.parseStatements(originalSql, DbType.postgresql);
        statements.forEach(statement -> {
            statement.accept(pgDataFilterVisitor);
        });
        String newSql = SQLUtils.toSQLString(statements, DbType.postgresql);
        System.out.println(newSql);
    }
}
