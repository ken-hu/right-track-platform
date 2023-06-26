package pers.ken.rt.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import pers.ken.rt.auth.domain.entity.aggregate.Group;
import pers.ken.rt.auth.domain.repository.GroupRepository;
import pers.ken.rt.auth.infrastructure.repository.persistence.mapper.UserGroupMapper;
import pers.ken.rt.common.model.PageQuery;

import java.util.List;

/**
 * @author Ken
 * @className: GroupRepositoryImpl
 * @createdTime: 2023/5/24 15:28
 * @desc:
 */
@Data
public class GroupRepositoryImpl implements GroupRepository {
    private UserGroupMapper userGroupMapper;

    @Override
    public List<Group> findListByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Group> pageQueryByTentId(PageQuery pageQuery) {
        userGroupMapper.selectPage(
                new Page<>(pageQuery.getPage(), pageQuery.getSize()),
                Wrappers.emptyWrapper()
        );
        return null;
    }
}
