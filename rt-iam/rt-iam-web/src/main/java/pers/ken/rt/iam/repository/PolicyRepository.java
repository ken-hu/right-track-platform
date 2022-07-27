package pers.ken.rt.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.ken.rt.iam.entity.Policy;

/**
 * <code> PolicyRepository </code>
 * <desc> PolicyRepository </desc>
 * <b>Creation Time:</b> 2022/7/27 15:58.
 *
 * @author Ken.Hu
 */
public interface PolicyRepository extends JpaRepository<Policy, Long> {

}
