package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.dto.req.PasswordRestRequest;
import pers.ken.rt.auth.dto.req.UserListRequest;
import pers.ken.rt.auth.dto.req.UserUpdateProfileRequest;
import pers.ken.rt.auth.repository.po.Account;
import pers.ken.rt.auth.repository.po.ThirdAccount;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【account(账户信息)】的数据库操作Service
 * @createDate 2024-12-05 16:33:02
 */
public interface AccountService extends IService<Account> {
    Account getByUsername(String username);

    Integer saveByThirdAccount(ThirdAccount thirdAccount);

    Account updateProfile(Integer id, UserUpdateProfileRequest request);

    void userDisable(Integer userId);

    List<Account> listByQuery(UserListRequest request);

    void resetPassword(PasswordRestRequest request);

    List<Account> listByUserGroup(Integer userGroupId);

}
