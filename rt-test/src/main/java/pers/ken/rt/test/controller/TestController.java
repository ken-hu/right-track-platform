package pers.ken.rt.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.iam.permission.access.AccessManagement;
import pers.ken.rt.iam.permission.access.AccessResource;
import pers.ken.rt.test.iam.TestResourceId;

/**
 * <code> TestController </code>
 * <desc> TestController </desc>
 * <b>Creation Time:</b> 2022/8/8 16:38.
 *
 * @author Ken.Hu
 */
@RestController
public class TestController {
    @GetMapping("/desc")
    @AccessManagement(action = "getDesc", resources = {
            @AccessResource(resource = TestResourceId.TEST, resourceValue = "#desc")
    })
    public String getDesc(String desc) {
        return desc + "1";
    }


    @GetMapping("/category")
    @AccessManagement(action = "category", resources = {
            @AccessResource(resource = TestResourceId.CITY, resourceValue = "#city"),
            @AccessResource(resource = TestResourceId.CATEGORY, resourceValue = "#category")
    })
    public String category(String city, String category) {
        return city + category + "OK";
    }
}
