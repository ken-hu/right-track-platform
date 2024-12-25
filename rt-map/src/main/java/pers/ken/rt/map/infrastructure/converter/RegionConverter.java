package pers.ken.rt.map.infrastructure.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.map.domain.entity.aggregate.Region;
import pers.ken.rt.map.infrastructure.repository.persistence.po.RegionPO;

import java.util.List;

/**
 * @ClassName: RegionConverter
 * @Created: 2024/11/5 10:32
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegionConverter {
    RegionConverter INSTANCE = Mappers.getMapper(RegionConverter.class);

    @Mapping(source = "parent", target = "parent")
    Region convert(RegionPO region);

    List<Region> convert(List<RegionPO> regions);
}
