package pers.ken.rt.auth.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pers.ken.rt.auth.domain.entity.aggregate.User;
import pers.ken.rt.auth.domain.exception.UserException;
import pers.ken.rt.auth.domain.repository.UserRepository;
import pers.ken.rt.auth.infrastructure.converter.UserConverter;
import pers.ken.rt.auth.infrastructure.repository.persistence.mapper.UserMapper;
import pers.ken.rt.auth.infrastructure.repository.persistence.po.UserPO;
import pers.ken.rt.common.exception.ServiceCode;
import pers.ken.rt.common.model.PageQuery;
import pers.ken.rt.common.model.PageResult;

import java.util.List;
import java.util.Optional;

/**
 * <name> OauthUserServiceImpl </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:44.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public User get(String username) {
        UserPO userPO = userMapper.selectOne(
                Wrappers.lambdaQuery(UserPO.class)
                        .eq(UserPO::getUsername, username)
        );
        return UserConverter.INSTANCE.convert(userPO);
    }

    @Override
    public User get(Long id) {
        UserPO userPO = Optional.ofNullable(userMapper.selectById(id))
                .orElseThrow(() -> new UserException(ServiceCode.MISSING_DATA));
        return UserConverter.INSTANCE.convert(userPO);
    }

    @Override
    public PageResult<User> pageQuery(PageQuery query) {
        IPage<UserPO> page = new Page<>(query.getPage(), query.getSize());
        IPage<UserPO> pageResult = userMapper.selectPage(page, Wrappers.emptyWrapper());
        List<User> content = UserConverter.INSTANCE.convert(pageResult.getRecords());
        return PageResult.of((int) pageResult.getPages(),
                (int) page.getSize(),
                pageResult.getTotal(), content);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user.getId(), "User not exist");
        UserPO userPO = userMapper.selectById(user.getId());
        userMapper.updateById(userPO);
    }
}
