package pers.ken.rt.mall.config;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import pers.ken.rt.common.web.SpringContextHolder;
import pers.ken.rt.mall.entity.Category;
import pers.ken.rt.mall.reporsitory.CategoryRepository;
import pers.ken.rt.pbac.permission.data.DataConditionGenerator;
import pers.ken.rt.pbac.permission.data.DataScopeSetting;
import pers.ken.rt.pbac.permission.data.IDataProvider;
import pers.ken.rt.pbac.permission.data.InMemoryDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: DataAccessConfig
 * @CreatedTime: 2023/1/11 17:15
 * @Desc:
 * @Author Ken
 */
@Configuration
public class AccessControlConfig {
    @Bean
    IDataProvider iDataProvider() {
        ArrayList<DataScopeSetting> dataScopeSettings = Lists.newArrayList(
                new DataScopeSetting(ResourceId.CATEGORY, "category", "code"),
                new DataScopeSetting(ResourceId.CATEGORY, "region", "adcode"),
                new DataScopeSetting(ResourceId.MULTI, "multi", "category_code", multiDataConditionGenerator()),
                new DataScopeSetting(ResourceId.MULTI, "multi", "adcode", multiDataConditionGenerator())
        );
        return new InMemoryDataProvider(dataScopeSettings);
    }

    @Bean
    DataConditionGenerator multiDataConditionGenerator() {
        return new DataConditionGenerator() {
            @Override
            public SQLExpr generateWhereCondition(String tableAlias, String field, Set<String> ownResources) {
                CategoryRepository categoryRepository = SpringContextHolder.getBean(CategoryRepository.class);
                List<Long> codes = categoryRepository.findAll().stream().map(Category::getCode).toList();
                if (StringUtils.hasText(tableAlias)) {
                    field = tableAlias.concat(".").concat(field);
                }
                List<SQLExpr> values = codes.stream()
                        .distinct()
                        .map(String::valueOf)
                        .map(SQLCharExpr::new)
                        .map(x -> (SQLExpr) x)
                        .toList();
                SQLInListExpr sqlInListExpr = new SQLInListExpr(field);
                sqlInListExpr.setTargetList(values);
                return sqlInListExpr;
            }
        };
    }
}
