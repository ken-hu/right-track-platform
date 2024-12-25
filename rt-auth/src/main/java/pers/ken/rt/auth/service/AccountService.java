package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.controller.req.PasswordRestReq;
import pers.ken.rt.auth.controller.req.UserListReq;
import pers.ken.rt.auth.controller.req.UserUpdateProfileReq;
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

    Account updateProfile(Integer id, UserUpdateProfileReq req);

    void disabledUser(Integer userId);

    List<Account> listByQuery(UserListReq req);

    void resetPassword(PasswordRestReq req);
}
