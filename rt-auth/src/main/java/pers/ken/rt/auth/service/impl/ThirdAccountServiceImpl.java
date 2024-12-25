package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ken.rt.auth.repository.mapper.ThirdAccountMapper;
import pers.ken.rt.auth.repository.po.ThirdAccount;
import pers.ken.rt.auth.service.AccountService;
import pers.ken.rt.auth.service.ThirdAccountService;

/**
 * @author DELL
 * @description 针对表【third_account】的数据库操作Service实现
 * @createDate 2024-12-05 16:33:02
 */
@Service
@RequiredArgsConstructor
public class ThirdAccountServiceImpl extends ServiceImpl<ThirdAccountMapper, ThirdAccount>
        implements ThirdAccountService {
    private final AccountService accountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ThirdAccount checkAndSaved(ThirdAccount oauth2ThirdAccount) {
        ThirdAccount thirdAccount = baseMapper.selectOne(Wrappers.lambdaQuery(ThirdAccount.class)
                .eq(ThirdAccount::getType, oauth2ThirdAccount.getType())
                .eq(ThirdAccount::getUniqueId, oauth2ThirdAccount.getUniqueId()));

        if (null == thirdAccount) {
            // 不存在则创建一个标准用户保存并关联保存一个第三方的账号的信息
            Integer accountId = accountService.saveByThirdAccount(oauth2ThirdAccount);
            thirdAccount = new ThirdAccount();
            BeanUtils.copyProperties(oauth2ThirdAccount, thirdAccount);
            thirdAccount.setAccountId(accountId);
            this.save(thirdAccount);
        } else {
            // 存在更新用户的认证信息
            thirdAccount.setCredentials(oauth2ThirdAccount.getCredentials());
            thirdAccount.setCredentialsExpiresAt(oauth2ThirdAccount.getCredentialsExpiresAt());
            this.updateById(thirdAccount);
        }
        return thirdAccount;
    }
}




