package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.dto.req.PasswordRestRequest;
import pers.ken.rt.auth.dto.req.UserListRequest;
import pers.ken.rt.auth.dto.req.UserUpdateProfileRequest;
import pers.ken.rt.auth.exception.AuthErrorCode;
import pers.ken.rt.auth.exception.PasswordResetException;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.oauth.utils.Pages;
import pers.ken.rt.auth.repository.mapper.AccountMapper;
import pers.ken.rt.auth.repository.po.Account;
import pers.ken.rt.auth.repository.po.ThirdAccount;
import pers.ken.rt.auth.service.AccountService;
import pers.ken.rt.common.exception.BusinessVerificationException;
import pers.ken.rt.common.exception.ErrorCode;
import pers.ken.rt.common.web.SpringContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        baseMapper.insert(account);
        return account.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account updateProfile(Integer id, UserUpdateProfileRequest request) {
        Account account = baseMapper.selectById(id);
        account.setNickname(request.getNickname());
        baseMapper.updateById(account);
        return account;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userDisable(Integer userId) {
        Account account = baseMapper.selectById(userId);
        if (Objects.isNull(account)) {
            throw new BusinessVerificationException(ErrorCode.DATA_NOT_FOUND, "Account not exists");
        }
        account.setStatus("disabled");
        baseMapper.updateById(account);
    }

    @Override
    public List<Account> listByQuery(UserListRequest request) {
        String tenantCode = AccountContext.getTenantCode();
        LambdaQueryWrapper<Account> query = Wrappers
                .lambdaQuery(Account.class)
            .eq(Account::getTenantCode, tenantCode);
        return baseMapper.selectList(Pages.toPage(request), query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(PasswordRestRequest request) {
        PasswordEncoder passwordEncoder = SpringContextHolder.getBean(PasswordEncoder.class);
        String oldPassword = request.getOldPassword();
        Integer userId = AccountContext.getUserId();
        Account account = getById(userId);
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            throw new PasswordResetException(ErrorCode.INVALID_ARGUMENTS, "Password error.");
        }
        if (account.getPassword().equals(passwordEncoder.encode(oldPassword))) {
            throw new PasswordResetException(AuthErrorCode.PASSWORD_REUSE_NOT_ALLOWED, "Previous passwords may not be reused.");
        }
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        baseMapper.updateById(account);
    }

    @Override
    public List<Account> listByUserGroup(Integer userGroupId) {
        return baseMapper.selectByUserGroup(userGroupId);
    }

}




