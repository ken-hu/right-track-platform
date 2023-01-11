package pers.ken.rt.mall.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.mall.entity.Region;

/**
 * @ClassName: RegionRepository
 * @CreatedTime: 2023/1/16 18:39
 * @Desc:
 * @Author Ken
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}
