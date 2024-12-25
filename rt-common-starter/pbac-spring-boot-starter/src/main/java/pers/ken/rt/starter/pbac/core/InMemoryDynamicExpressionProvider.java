package pers.ken.rt.starter.pbac.core;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: InMemoryDynamicExpressionProvider
 * @Created: 2024/7/23
 * @Author ken
 */
@Data
public class InMemoryDynamicExpressionProvider implements DynamicExpressionsProvider {
    @Override
    public List<Map<String, String>> getValuesByVariableName(List<String> variableName) {
        Map<String, String> context = new HashMap<>() {{
            put("authority_city", "100100");
        }};

        Map<String, String> context2 = new HashMap<>() {{
            put("authority_city", "440100");
        }};

        return Lists.newArrayList(context, context2);
    }

}
