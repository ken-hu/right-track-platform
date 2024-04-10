package pers.ken.rt.auth.infrastructure.repository.impl;

import org.springframework.stereotype.Service;
import pers.ken.rt.auth.domain.entity.aggregate.Role;
import pers.ken.rt.auth.domain.repository.RoleRepository;

import java.util.List;

/**
 * <code> RoleServiceImpl </code>
 * <desc> RoleServiceImpl </desc>
 * <b>Creation Time:</b> 2022/6/6 11:31.
 *
 * @author Ken.Hu
 */
@Service
public class RoleRepositoryImpl implements RoleRepository {
    @Override
    public void create() {

    }

    @Override
    public List<Role> findListByUserId(Long userId) {
        return null;
    }
}
