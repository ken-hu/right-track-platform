package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.controller.req.PasswordRestReq;
import pers.ken.rt.auth.controller.req.UserListReq;
import pers.ken.rt.auth.controller.req.UserUpdateProfileReq;
import pers.ken.rt.auth.exception.AuthErrorCode;
import pers.ken.rt.auth.exception.PasswordResetException;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.mapper.AccountMapper;
import pers.ken.rt.auth.repository.po.Account;
import pers.ken.rt.auth.repository.po.ThirdAccount;
import pers.ken.rt.auth.service.AccountService;
import pers.ken.rt.common.web.SpringContextHolder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author DELL
 * @description 针对表【account(账户信息)】的数据库操作Service实现
 * @createDate 2024-12-05 16:33:02
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {

    @Override
    public Account getByUsername(String username) {
        return baseMapper.selectOne(
                Wrappers.lambdaQuery(Account.class)
                        .eq(Account::getUsername, username)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveByThirdAccount(ThirdAccount thirdAccount) {
        Account account = new Account();
        account.setNickname(thirdAccount.getNickname());
        account.setRegisteredFrom(thirdAccount.getType());
        account.setRegisteredAt(LocalDateTime.now());
        this.save(account);
        return account.getId();
    }

    @Override
    @Transactional
    public Account updateProfile(Integer id, UserUpdateProfileReq req) {
        Account account = getById(id);
        account.setNickname(req.getNickname());
        this.updateById(account);
        return account;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disabledUser(Integer userId) {
        Account account = getById(userId);
        account.setStatus("disabled");
        this.updateById(account);
    }

    @Override
    public List<Account> listByQuery(UserListReq req) {
        String tenantId = AccountContext.getTenantCode();
        LambdaQueryWrapper<Account> query = Wrappers
                .lambdaQuery(Account.class)
                .eq(Account::getTenantCode, tenantId);
        return this.list(new Page<>(req.getPage(), req.getPerPage()), query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(PasswordRestReq req) {
        PasswordEncoder passwordEncoder = SpringContextHolder.getBean(PasswordEncoder.class);
        String oldPassword = req.getOldPassword();
        Integer userId = AccountContext.getUserId();
        Account account = getById(userId);
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            throw new PasswordResetException(AuthErrorCode.PASSWORD_VERIFICATION_FAILED, "Password authentication failure");
        }
        if (account.getPassword().equals(passwordEncoder.encode(oldPassword))) {
            throw new PasswordResetException(AuthErrorCode.PASSWORD_VERIFICATION_FAILED, "Password cannot be the same as the old one");
        }
        account.setPassword(passwordEncoder.encode(req.getNewPassword()));
        updateById(account);
    }

}




