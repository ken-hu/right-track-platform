package pers.ken.rt.auth.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.auth.application.assembler.UserAssembler;
import pers.ken.rt.auth.application.service.UserAppService;
import pers.ken.rt.auth.domain.entity.aggregate.User;
import pers.ken.rt.auth.domain.entity.valueobj.UserStatus;
import pers.ken.rt.auth.domain.repository.UserRepository;
import pers.ken.rt.auth.interfaces.dto.resp.UserItemResponse;
import pers.ken.rt.common.model.PageQuery;
import pers.ken.rt.common.model.PageResult;

import java.util.List;

/**
 * @author Ken
 * @className: UserAppServiceImpl
 * @createdTime: 2023/3/8 1:05
 * @desc:
 */
@Service
@AllArgsConstructor
public class UserAppServiceImpl implements UserAppService {
    private UserRepository userRepository;

    @Override
    public PageResult<UserItemResponse> pageQuery(PageQuery pageReq) {
        PageResult<User> pageResult = userRepository.pageQuery(pageReq);
        List<UserItemResponse> records = UserAssembler.INSTANCE.convert(pageResult.getRecords());
        return PageResult.of(pageResult, records);
    }

    @Override
    public void updateUserStatus(Long userId, UserStatus userStatus) {
        User user = userRepository.get(userId);
        switch (userStatus) {
            case DISABLE -> {
                user.disableUser();
                break;
            }
            case ENABLE -> {
                user.enabledUser();
                break;
            }
            default -> {
                return;
            }
        }
        userRepository.update(user);
    }
}
