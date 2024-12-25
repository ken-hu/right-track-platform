package pers.ken.rt.map.interfaces.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.map.infrastructure.config.MapResource;
import pers.ken.rt.starter.pbac.annotation.AccessManager;
import pers.ken.rt.starter.pbac.annotation.AccessManagerService;
import pers.ken.rt.starter.pbac.annotation.Rn;

/**
 * @ClassName: PoiController
 * @Created: 2024/11/4 11:39
 * @Author ken
 */
@RestController
@Slf4j
@AccessManagerService("map")
public class PoiController {
    @GetMapping("/v1/region/{adcode}/pois")
    @AccessManager(@Rn(id = MapResource.REGION, value = "#adcode"))
    public void pois(@PathVariable Integer adcode) {
        log.info("poi list test:{}", adcode);
    }
}
