package pers.ken.rt.uaa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.uaa.entity.Role;

/**
 * <name> RoleRepository </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 22:49.
 *
 * @author _Ken.Hu
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
