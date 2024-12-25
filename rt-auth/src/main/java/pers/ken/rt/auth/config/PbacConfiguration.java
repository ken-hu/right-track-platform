package pers.ken.rt.auth.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pers.ken.rt.auth.service.PolicyService;
import pers.ken.rt.auth.service.support.AuthPolicyProvider;
import pers.ken.rt.starter.pbac.annotation.PbacAccessControlEnable;
import pers.ken.rt.starter.pbac.core.DynamicExpressionsProvider;
import pers.ken.rt.starter.pbac.core.PolicyProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PbacConfiguration
 * @Created: 2024/12/9 11:15
 * @Author ken
 */
@Configuration
@Slf4j
@PbacAccessControlEnable
public class PbacConfiguration {

    @Bean
    public PolicyProvider policyProvider(PolicyService policyService) {
        return new AuthPolicyProvider(policyService);
    }


    @Bean
    @Primary
    public DynamicExpressionsProvider dynamicExpressionProvider() {
        return new DynamicExpressionsProvider() {
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
        };
    }
}
