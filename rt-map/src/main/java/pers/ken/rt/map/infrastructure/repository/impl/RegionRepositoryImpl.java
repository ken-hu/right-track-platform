package pers.ken.rt.map.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pers.ken.rt.map.domain.entity.aggregate.Region;
import pers.ken.rt.map.domain.repository.RegionRepository;
import pers.ken.rt.map.infrastructure.converter.RegionConverter;
import pers.ken.rt.map.infrastructure.repository.persistence.mapper.RegionPOMapper;
import pers.ken.rt.map.infrastructure.repository.persistence.po.RegionPO;

import java.util.List;

/**
 * @ClassName: RegionRepositoryImpl
 * @Created: 2024/11/5 10:18
 * @Author ken
 */
@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {
    private final RegionPOMapper regionMapper;

    @Override
    public List<Region> findCities(Integer provinceCode, String adlevel) {
        List<RegionPO> cities = regionMapper.selectList(Wrappers.lambdaQuery(RegionPO.class)
                .eq(RegionPO::getAdlevel, adlevel));

        return RegionConverter.INSTANCE.convert(cities);
    }
}
