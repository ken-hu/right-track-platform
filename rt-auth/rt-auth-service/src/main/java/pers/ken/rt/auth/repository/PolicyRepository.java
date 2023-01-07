package pers.ken.rt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.auth.entity.Policy;

/**
 * <code> PolicyRepository </code>
 * <desc> PolicyRepository </desc>
 * <b>Creation Time:</b> 2021/12/3 13:49.
 *
 * @author _Ken.Hu
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

}