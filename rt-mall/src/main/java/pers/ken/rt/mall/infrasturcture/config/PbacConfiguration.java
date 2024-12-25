package pers.ken.rt.mall.infrasturcture.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;
import pers.ken.rt.starter.pbac.permission.api.InMemoryPolicyProvider;

import java.util.List;

/**
 * @ClassName: PbacConfiguration
 * @Created: 2024/7/3
 * @Author ken
 */
@Configuration
public class PbacConfiguration {
    @Bean
    public InMemoryPolicyProvider policyProvider() {
        String policiesJson = """
                [
                    {
                        "id": "id_90ca5bd38344",
                        "version": "version_f35013c94d48",
                        "name": "name_5fe91a556fdf",
                        "description": "description_f03b843b1832",
                        "statements": [
                            {
                                "id": "test",
                                "effect": "Allow",
                                "actions": [
                                    "rt:TestGet"
                                ],
                                "resources": [
                                    "rt:category/*"
                                ]
                            }
                        ]
                    },
                    {
                        "id": "id_90ca5bd383442",
                        "version": null,
                        "name": "name_5fe91a556fdf",
                        "description": "description_f03b843b1832",
                        "statements": [
                            {
                                "id": "test",
                                "effect": "Deny",
                                "actions": [
                                    "rt:TestGet"
                                ],
                                "resources": [
                                    "rt:category/123",
                                    "rt:category/124"
                                ]
                            }
                        ]
                    },
                    {
                        "id": "id_90ca5bd383443",
                        "version": null,
                        "name": "name_5fe91a556fdf",
                        "description": "description_f03b843b1832",
                        "statements": [
                            {
                                "id": "test",
                                "effect": "Allow",
                                "actions": [
                                    "rt:GetCityV1",
                                    "rt:GetCityCategoryV1"
                                ],
                                "resources": [
                                    "rt:region/${authority_city}",
                                    "rt:category/11111",
                                    "rt:category/2"
                                ]
                            }
                        ]
                    }
                ]
                """;
        List<PolicyDocument> policies = Jackson.fromJsonString(policiesJson, new TypeReference<List<PolicyDocument>>() {
        });
        InMemoryPolicyProvider policyProvider = new InMemoryPolicyProvider();
        policyProvider.addPolicies(policies);
        return policyProvider;
    }
}
