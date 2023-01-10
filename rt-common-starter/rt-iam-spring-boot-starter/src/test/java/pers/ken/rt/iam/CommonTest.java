package pers.ken.rt.iam;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import pers.ken.rt.iam.internal.Policy;
import pers.ken.rt.iam.utils.Jackson;
import pers.ken.rt.iam.utils.PolicyResolver;

import java.util.Collections;

/**
 * @ClassName: CommonTest
 * @CreatedTime: 2023/1/10 18:16
 * @Desc:
 * @Author Ken
 */
public class CommonTest {
    @Test
    void testJsonNode() {
        // what you can get
        // aop : actionId method value
        // resource: spel

        String actionId = "mars:PostMapDimAmapConfSave";
        String resource = "channel:mars:popType:1";

        String policiesJson = """
                {
                  "id": "null",
                  "version": "1.0",
                  "statement": [
                    {
                      "id": null,
                      "action": [
                        "mars:PostMapDimAmapConfSave",
                        "mars:GetMapDimAmapConfUserAmapInfo",
                        "mars:GetMapAoiBusinessCategoryList",
                        "mars:PostMapProvinceCategoryRanking",
                        "mars:GetMapProvinceCategoryIndicator"
                      ],
                      "effect": "Allow",
                      "resource": [
                        "channel:mars:evaluateDataSource:1",
                        "channel:mars:businessCircle:*",
                        "channel:mars:popType:*",
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
        Policy policy = Jackson.fromJsonString(policiesJson, new TypeReference<Policy>() {
        });

        boolean permit = PolicyResolver.isPermit(Collections.singletonList(policy), resource, actionId);
        System.out.println(permit);
    }
}
