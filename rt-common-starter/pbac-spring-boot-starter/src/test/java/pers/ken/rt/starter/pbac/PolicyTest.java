package pers.ken.rt.starter.pbac;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.starter.pbac.core.InMemoryDynamicExpressionProvider;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;
import pers.ken.rt.starter.pbac.permission.api.ActionProvider;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @ClassName: CommonTest
 * @CreatedTime: 2023/1/10 18:16
 * @Desc:
 * @Author Ken
 */
@Slf4j
public class PolicyTest {

    private static final Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");

    @Test
    @DisplayName("actionName转换")
    public void actionTest() {
        String actionName = ActionProvider.convertURItoActionName("service", "GET", "/v1/city/{code}/pois", List.class);
        Assertions.assertEquals(actionName, "ListCityPoisV1");
    }

    @Test
    public void dynamicVariableTest() {
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

        InMemoryDynamicExpressionProvider inMemoryDynamicExpressionProvider = new InMemoryDynamicExpressionProvider();

        policies.forEach(policy -> {
            policy.getStatements().forEach(statement -> {
                System.out.println(Jackson.toJsonString(statement));
                PolicyDocument.Statement newStatement = inMemoryDynamicExpressionProvider.dynamicParserExpressions(statement);
                System.out.println(Jackson.toJsonString(statement));
                System.out.println(Jackson.toJsonString(newStatement));
            });
        });
    }

}
