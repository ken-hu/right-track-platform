package pers.ken.rt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.ken.rt.auth.entity.Enterprise;

/**
 * <code> EnterpriseRepository </code>
 * <desc> EnterpriseRepository </desc>
 * <b>Creation Time:</b> 2022/2/12 1:26.
 *
 * @author _Ken.Hu
 */
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}
