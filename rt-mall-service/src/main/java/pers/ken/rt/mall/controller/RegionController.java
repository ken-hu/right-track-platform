package pers.ken.rt.mall.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.mall.config.ResourceId;
import pers.ken.rt.mall.entity.Region;
import pers.ken.rt.mall.reporsitory.RegionRepository;
import pers.ken.rt.starter.pbac.anno.AccessControl;
import pers.ken.rt.starter.pbac.anno.Resource;

import java.util.List;

/**
 * @ClassName: RegionController
 * @CreatedTime: 2023/1/16 18:25
 * @Desc:
 * @Author Ken
 */
@RestController
@Tag(name = "region")
@AllArgsConstructor
public class RegionController {
    private RegionRepository regionRepository;

    @GetMapping("/regions")
    @AccessControl(actionId = "ListRegions",
            resources = @Resource(id = ResourceId.REGION, value = "#adcode"))
    public List<Region> listRegions(Long adcode) {
        return regionRepository.findAll();
    }
}
