package pers.ken.rt.uc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.uc.entity.Role;
import pers.ken.rt.uc.entity.User;

/**
 * <name> UserRepository </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:55.
 *
 * @author _Ken.Hu
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
