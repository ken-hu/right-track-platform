package pers.ken.rt.map.domain.repository;

import pers.ken.rt.map.domain.entity.aggregate.Region;

import java.util.List;

/**
 * The interface Region repository.
 *
 * @ClassName: RegionRepository
 * @Created: 2024 /11/4 18:25
 * @Author ken
 */
public interface RegionRepository {

    List<Region> findCities(Integer provinceCode, String adlevel);
}
