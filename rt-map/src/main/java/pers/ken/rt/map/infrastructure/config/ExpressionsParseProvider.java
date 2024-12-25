package pers.ken.rt.map.infrastructure.config;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import pers.ken.rt.starter.pbac.core.DynamicExpressionsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DynamicExpressionsProvider
 * @Created: 2024/11/4 18:13
 * @Author ken
 */
@Component
public class ExpressionsParseProvider implements DynamicExpressionsProvider {

    @Override
    public List<Map<String, String>> getValuesByVariableName(List<String> variableName) {
        Map<String, String> context = new HashMap<>() {{
            put("authority_city", "500100");
        }};

        Map<String, String> context2 = new HashMap<>() {{
            put("authority_city", "440100");
        }};
        return Lists.newArrayList(context, context2);
    }
}
