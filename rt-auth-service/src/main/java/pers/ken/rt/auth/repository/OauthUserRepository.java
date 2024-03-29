package pers.ken.rt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.auth.entity.OauthUser;

/**
 * <name> UserRepository </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:55.
 *
 * @author _Ken.Hu
 */
@Repository
public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {
    /**
     * Find by username oauth user.
     *
     * @param username the username
     * @return the oauth user
     */
    OauthUser findByUsername(String username);
}
