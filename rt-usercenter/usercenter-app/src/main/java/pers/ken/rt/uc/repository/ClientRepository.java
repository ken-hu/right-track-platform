package pers.ken.rt.uc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.uc.entity.Client;

/**
 * <name> ClientRepository </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:56.
 *
 * @author _Ken.Hu
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
