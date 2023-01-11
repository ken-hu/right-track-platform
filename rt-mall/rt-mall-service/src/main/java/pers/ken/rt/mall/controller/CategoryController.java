package pers.ken.rt.mall.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.mall.config.ResourceId;
import pers.ken.rt.mall.entity.Category;
import pers.ken.rt.mall.reporsitory.CategoryRepository;
import pers.ken.rt.pbac.permission.access.AccessControl;
import pers.ken.rt.pbac.permission.access.Resource;

/**
 * @ClassName: CategoryController
 * @CreatedTime: 2023/1/11 11:47
 * @Desc:
 * @Author Ken
 */
@RestController
@Tag(name = "category")
@AllArgsConstructor
public class CategoryController {
    private CategoryRepository categoryRepository;

    @GetMapping("/categories/{code}")
    @AccessControl(actionId = "getCategory",
            resources = {
                    @Resource(id = ResourceId.CATEGORY, value = "#code")
            })
    public Category getCategory(@PathVariable Long code) {
        return categoryRepository.findByCode(code);
    }
}
