package pers.ken.rt.uaa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.uaa.entity.OauthClient;

/**
 * <name> ClientRepository </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:56.
 *
 * @author _Ken.Hu
 */
@Repository
public interface UcClientRepository extends JpaRepository<OauthClient, Long> {

    /**
     * Find by client id oauth client.
     *
     * @param clientId the client id
     * @return the oauth client
     */
    OauthClient findByClientId(String clientId);
}
