package pers.ken.rt.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.ken.rt.test.entity.Category;

import java.util.List;

/**
 * Creation Time: 2022/11/15 11:11.
 *
 * @author _Ken.Hu
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Gets category by ic code.
     *
     * @param icCode the ic code
     * @return the category by ic code
     */
    List<Category> getCategoryByIcCode(Long icCode);
}
