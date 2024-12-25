package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.repository.po.ThirdAccount;

/**
 * @author DELL
 * @description 针对表【third_account】的数据库操作Service
 * @createDate 2024-12-05 16:33:02
 */
public interface ThirdAccountService extends IService<ThirdAccount> {
    ThirdAccount checkAndSaved(ThirdAccount oauth2ThirdAccount);
}
