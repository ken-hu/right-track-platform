package pers.ken.rt.mall.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.mall.config.ResourceId;
import pers.ken.rt.mall.entity.Multi;
import pers.ken.rt.mall.reporsitory.MultiRepository;
import pers.ken.rt.starter.pbac.anno.AccessControl;
import pers.ken.rt.starter.pbac.anno.Resource;

import java.util.List;

/**
 * @ClassName: MultiElementController
 * @CreatedTime: 2023/1/11 11:49
 * @Desc:
 * @Author Ken
 */
@RestController
@Tag(name = "multi-element")
@AllArgsConstructor
public class MultiElementController {
    private MultiRepository multiRepository;

    @GetMapping("/element1/{code1}/element2/{code2}")
    @AccessControl(actionId = "getMultiElement",
            resources = @Resource(id = ResourceId.MULTI, value = "#code1+'/'+#code2"))
    public List<Multi> getMultiElement(@PathVariable Long code1, @PathVariable Long code2) {
        return multiRepository.findByAdcodeAndCategoryCode(code1, code2);
    }
}
