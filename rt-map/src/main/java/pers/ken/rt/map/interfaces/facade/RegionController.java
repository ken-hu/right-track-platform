package pers.ken.rt.map.interfaces.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.map.domain.entity.aggregate.Region;
import pers.ken.rt.map.domain.repository.RegionRepository;
import pers.ken.rt.starter.pbac.annotation.AccessManager;
import pers.ken.rt.starter.pbac.annotation.AccessManagerService;

import java.util.List;

/**
 * @ClassName: RegionController
 * @Created: 2024/11/4 14:45
 * @Author ken
 */
@RestController
@AccessManagerService("map")
@RequiredArgsConstructor
public class RegionController {
    private final RegionRepository regionRepository;

    @GetMapping("/v1/region/cities")
    @AccessManager
    public List<Region> cityList() {
        return regionRepository.findCities(null, "CITY");
    }
}
