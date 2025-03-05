package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.auth.dto.req.ApplicationCreateRequest;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.mapper.ApplicationMapper;
import pers.ken.rt.auth.repository.po.Application;
import pers.ken.rt.auth.repository.po.TenantApplicationAuthorization;
import pers.ken.rt.auth.service.ApplicationService;
import pers.ken.rt.auth.service.TenantApplicationAuthorizationService;
import pers.ken.rt.common.exception.BusinessVerificationException;
import pers.ken.rt.common.exception.ErrorCode;
import pers.ken.rt.common.utils.NanoIdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DELL
 * @description 针对表【application】的数据库操作Service实现
 * @createDate 2024-12-09 20:18:04
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application>
    implements ApplicationService {

    private final TenantApplicationAuthorizationService tenantApplicationAuthorizationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Application create(ApplicationCreateRequest request) {
        Application application = getByCode(request.getAppCode());
        if (null != application) {
            throw new BusinessVerificationException(ErrorCode.BUSINESS_ERROR, "ApplicationCode: '%s' repeat.".formatted(application.getAppCode()));
        }

        String applicationCode = request.getAppCode();
        if (StringUtils.isBlank(applicationCode)) {
            applicationCode = NanoIdGenerator.generate();
        }
        Application newApplication = new Application();
        newApplication.setName(request.getName());
        newApplication.setAppCode(applicationCode);
        newApplication.setDescription(request.getDescription());
        newApplication.setIndexUrl(request.getIndexUrl());

        this.save(newApplication);
        return newApplication;
    }

    @Override
    public Application getByCode(String code) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(Application.class)
                .eq(Application::getAppCode, code), false
        );
    }

    @Override
    public List<Application> listByTenant() {
        if (AccountContext.isAdmin()) {
            return list(Wrappers.emptyWrapper());
        }

        List<TenantApplicationAuthorization> authorizations = tenantApplicationAuthorizationService.list(
            Wrappers.lambdaQuery(TenantApplicationAuthorization.class)
                .eq(TenantApplicationAuthorization::getTenantId, AccountContext.getTenantId()));

        if (CollectionUtils.isEmpty(authorizations)) {
            return new ArrayList<>();
        }
        List<Integer> applicationIds = authorizations
            .stream()
            .map(TenantApplicationAuthorization::getAppId)
            .toList();
        return baseMapper.selectList(Wrappers.lambdaQuery(Application.class)
            .in(Application::getId, applicationIds));
    }
}




