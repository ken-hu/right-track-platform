package pers.ken.rt.mall.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.mall.entity.Category;

/**
 * The interface Category repository.
 *
 * @ClassName: CategoryRepository
 * @CreatedTime: 2023 /1/16 18:39
 * @Desc:
 * @Author Ken
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find by code category.
     *
     * @param code the code
     * @return the category
     */
    Category findByCode(Long code);
}
