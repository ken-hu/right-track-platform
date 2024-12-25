package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.ken.rt.auth.repository.po.TenantApplicationAuthorization;
import pers.ken.rt.auth.service.TenantApplicationAuthorizationService;
import pers.ken.rt.auth.repository.mapper.TenantApplicationAuthorizationMapper;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @description 针对表【tenant_application_authorization(租户的应用授权)】的数据库操作Service实现
 * @createDate 2024-12-25 14:25:37
 */
@Service
public class TenantApplicationAuthorizationServiceImpl extends ServiceImpl<TenantApplicationAuthorizationMapper, TenantApplicationAuthorization>
        implements TenantApplicationAuthorizationService {

}




