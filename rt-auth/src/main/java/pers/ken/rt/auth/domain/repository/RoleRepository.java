package pers.ken.rt.auth.domain.repository;

import pers.ken.rt.auth.domain.entity.aggregate.Role;

import java.util.List;

/**
 * <name> RoleService </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 22:51.
 *
 * @author _Ken.Hu
 */
public interface RoleRepository {

    /**
     * Create.
     */
    void create();

    /**
     * Find list by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Role> findListByUserId(Long userId);
}
