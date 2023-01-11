package pers.ken.rt.mall.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.mall.entity.Multi;

import java.util.List;

/**
 * The interface Multi repository.
 *
 * @ClassName: MultiRepository
 * @CreatedTime: 2023 /1/16 18:39
 * @Desc:
 * @Author Ken
 */
@Repository
public interface MultiRepository extends JpaRepository<Multi, Long> {
    /**
     * Find by adcode and category code list.
     *
     * @param adcode       the adcode
     * @param categoryCode the category code
     * @return the list
     */
    List<Multi> findByAdcodeAndCategoryCode(Long adcode, Long categoryCode);
}
