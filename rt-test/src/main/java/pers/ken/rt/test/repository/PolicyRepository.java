package pers.ken.rt.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.ken.rt.test.entity.AuthPolicy;

import java.util.List;

/**
 * Creation Time: 2022/11/15 11:11.
 *
 * @author _Ken.Hu
 */
public interface PolicyRepository extends JpaRepository<AuthPolicy, Long> {

    /**
     * Gets auth policy by user id.
     *
     * @param userId the user id
     * @return the auth policy by user id
     */
    List<AuthPolicy> getAuthPolicyByUserId(Long userId);
}
