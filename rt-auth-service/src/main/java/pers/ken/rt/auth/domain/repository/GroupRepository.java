package pers.ken.rt.auth.domain.repository;

import pers.ken.rt.auth.domain.entity.aggregate.Group;
import pers.ken.rt.common.model.PageQuery;

import java.util.List;

/**
 * The interface Group repository.
 *
 * @author Ken
 * @className: GroupRepository
 * @createdTime: 2023 /3/10 1:21
 * @desc:
 */
public interface GroupRepository {
    /**
     * Find list by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Group> findListByUserId(Long userId);

    /**
     * Page query by tent id list.
     *
     * @param pageQuery the page query
     * @return the list
     */
    List<Group> pageQueryByTentId(PageQuery pageQuery);
}
