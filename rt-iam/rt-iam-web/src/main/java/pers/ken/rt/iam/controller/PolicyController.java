package pers.ken.rt.iam.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.iam.dto.req.CreatePolicyReq;
import pers.ken.rt.iam.dto.req.TestPolicyCreateReq;
import pers.ken.rt.iam.service.PolicyService;

/**
 * <code> PolicyController </code>
 * <desc> PolicyController </desc>
 * <b>Creation Time:</b> 2022/7/19 17:33.
 *
 * @author Ken.Hu
 */
@RestController
@AllArgsConstructor
@Tag(name = "权限策略")
public class PolicyController {
    private PolicyService policyService;

    @Operation(summary = "查询用户策略", operationId = "listUserPolicies")
    @GetMapping(value = "/users/{userId}/policies", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listUserPolicies(@PathVariable("userId") String userId) {
//        Jackson.fromJsonString("[{\n" +
//                "        \"id\": \"null\",\n" +
//                "        \"version\": \"1.0\",\n" +
//                "        \"statements\": [\n" +
//                "            {\n" +
//                "                \"id\": null,\n" +
//                "                \"actions\": [\n" +
//                "                    \"mars:PostMapDimAmapConfSave\",\n" +
//                "                    \"mars:GetMapDimAmapConfUserAmapInfo\",\n" +
//                "                    \"mars:GetMapAoiBusinessCategoryList\",\n" +
//                "                    \"mars:PostMapProvinceCategoryRanking\",\n" +
//                "                    \"mars:GetMapProvinceCategoryIndicator\",\n" +
//                "                    \"category\",\n" +
//                "                    \"getDesc\"\n" +
//                "                ],\n" +
//                "                \"effect\": \"Allow\",\n" +
//                "                \"resources\": [\n" +
//                "                    \"channel:mars:evaluateDataSource:1\",\n" +
//                "                    \"channel:mars:businessCircle:*\",\n" +
//                "                    \"channel:mars:popType:1\",\n" +
//                "                    \"channel:mars:popType:2\",\n" +
//                "                    \"channel:mars:popType:3\",\n" +
//                "                    \"channel:mars:heatType:*\",\n" +
//                "                    \"channel:mars:city:${adcode}\",\n" +
//                "                    \"channel:mars:poi:*/*\",\n" +
//                "                    \"channel:mars:industryCategory:*\",\n" +
//                "                    \"channel:mars:contrastAnalysisSelector:*\",\n" +
//                "                    \"map:test/*\",\n" +
//                "                    \"test/*\",\n" +
//                "                    \"heat/*\",\n" +
//                "                    \"nice:city/*/category/*\"\n" +
//                "                ]\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }]", new TypeReference<List<Policy>>() {
//        });
        return "[{\n" +
                "        \"id\": \"null\",\n" +
                "        \"version\": \"1.0\",\n" +
                "        \"statements\": [\n" +
                "            {\n" +
                "                \"id\": null,\n" +
                "                \"actions\": [\n" +
                "                    \"mars:PostMapDimAmapConfSave\",\n" +
                "                    \"mars:GetMapDimAmapConfUserAmapInfo\",\n" +
                "                    \"mars:GetMapAoiBusinessCategoryList\",\n" +
                "                    \"mars:PostMapProvinceCategoryRanking\",\n" +
                "                    \"mars:GetMapProvinceCategoryIndicator\",\n" +
                "                    \"category\",\n" +
                "                    \"getDesc\"\n" +
                "                ],\n" +
                "                \"effect\": \"Allow\",\n" +
                "                \"resources\": [\n" +
                "                    \"channel:mars:evaluateDataSource:1\",\n" +
                "                    \"channel:mars:businessCircle:*\",\n" +
                "                    \"channel:mars:popType:1\",\n" +
                "                    \"channel:mars:popType:2\",\n" +
                "                    \"channel:mars:popType:3\",\n" +
                "                    \"channel:mars:heatType:*\",\n" +
                "                    \"channel:mars:city:${adcode}\",\n" +
                "                    \"channel:mars:poi:*/*\",\n" +
                "                    \"channel:mars:industryCategory:*\",\n" +
                "                    \"channel:mars:contrastAnalysisSelector:*\",\n" +
                "                    \"map:test/*\",\n" +
                "                    \"test/*\",\n" +
                "                    \"heat/*\",\n" +
                "                    \"nice:city/*/category/*\"\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }]";
    }

    @Operation(summary = "创建策略", operationId = "createPolicies")
    @PostMapping(value = "/policies", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createPolicy(@RequestBody CreatePolicyReq createPolicyReq) {
    }

    @Operation(summary = "查询策略", operationId = "listPolicies")
    @GetMapping(value = "/user-policies", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listPolicies() {
        return "[{\n" +
                "        \"id\": \"null\",\n" +
                "        \"version\": \"1.0\",\n" +
                "        \"statements\": [\n" +
                "            {\n" +
                "                \"id\": null,\n" +
                "                \"actions\": [\n" +
                "                    \"mars:PostMapDimAmapConfSave\",\n" +
                "                    \"mars:GetMapDimAmapConfUserAmapInfo\",\n" +
                "                    \"mars:GetMapAoiBusinessCategoryList\",\n" +
                "                    \"mars:PostMapProvinceCategoryRanking\",\n" +
                "                    \"mars:GetMapProvinceCategoryIndicator\",\n" +
                "                    \"category\",\n" +
                "                    \"getDesc\"\n" +
                "                ],\n" +
                "                \"effect\": \"Allow\",\n" +
                "                \"resources\": [\n" +
                "                    \"channel:mars:evaluateDataSource:1\",\n" +
                "                    \"channel:mars:businessCircle:*\",\n" +
                "                    \"channel:mars:popType:1\",\n" +
                "                    \"channel:mars:popType:2\",\n" +
                "                    \"channel:mars:popType:3\",\n" +
                "                    \"channel:mars:heatType:*\",\n" +
                "                    \"channel:mars:city:${adcode}\",\n" +
                "                    \"channel:mars:poi:*/*\",\n" +
                "                    \"channel:mars:industryCategory:*\",\n" +
                "                    \"channel:mars:contrastAnalysisSelector:*\",\n" +
                "                    \"map:test/*\",\n" +
                "                    \"test/*\",\n" +
                "                    \"heat/*\",\n" +
                "                    \"nice:city/*/category/*\"\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }]";
    }

    @PostMapping(value = "/policies/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public void testCreate(TestPolicyCreateReq testPolicyCreateReq){
        
    }

}
