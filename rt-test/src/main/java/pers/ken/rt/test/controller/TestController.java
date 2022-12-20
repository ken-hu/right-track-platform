package pers.ken.rt.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.iam.permission.access.AccessDenyException;
import pers.ken.rt.iam.permission.access.AccessManagement;
import pers.ken.rt.iam.permission.access.AccessResource;
import pers.ken.rt.test.entity.Category;
import pers.ken.rt.test.iam.TestResourceId;
import pers.ken.rt.test.repository.CategoryRepository;

import java.util.List;

/**
 * <code> TestController </code>
 * <desc> TestController </desc>
 * <b>Creation Time:</b> 2022/8/8 16:38.
 *
 * @author Ken.Hu
 */
@RestController
@AllArgsConstructor
public class TestController {
    private CategoryRepository categoryRepository;

    @GetMapping("/desc")
    @AccessManagement(action = "getDesc",
            resources = {
                    @AccessResource(resource = TestResourceId.TEST, resourceValue = "#desc")
            })
    public String getDesc(String desc) {
        List<Category> category = categoryRepository.getCategoryByIcCode(10000L);
        if ("1".equals(desc)) {
            throw new AccessDenyException("......");
        }
        return Jackson.toJsonString(category);
    }


    @GetMapping("/category")
    @AccessManagement(
            action = "category",
            resources = {
                    @AccessResource(resource = TestResourceId.CITY, resourceValue = "#city"),
                    @AccessResource(resource = TestResourceId.CATEGORY, resourceValue = "#category")
            })
    public String category(String city, String category) {
        List<Category> categories = categoryRepository.getCategoryByIcCode(10000L);
        return city + category + Jackson.toJsonString(categories);
    }
}
