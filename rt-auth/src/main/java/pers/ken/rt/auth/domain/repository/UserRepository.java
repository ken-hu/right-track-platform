package pers.ken.rt.auth.domain.repository;

import pers.ken.rt.auth.domain.entity.aggregate.User;
import pers.ken.rt.common.model.PageResult;
import pers.ken.rt.common.model.Pagination;

/**
 * <name> OauthUserService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:43.
 *
 * @author _Ken.Hu
 */
public interface UserRepository {
    /**
     * Gets oauth user.
     *
     * @param username the username
     * @return the oauth user
     */
    User get(String username);

    /**
     * Gets oauth user.
     *
     * @param id the id
     * @return the oauth user
     */
    User get(Long id);

    /**
     * Find list list.
     *
     * @param pagination the page query
     * @return the list
     */
    PageResult<User> pageQuery(Pagination pagination);

    /**
     * Update.
     *
     * @param user the user
     */
    void update(User user);
}
