package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.repository.po.Application;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【application】的数据库操作Service
 * @createDate 2024-12-09 20:18:04
 */
public interface ApplicationService extends IService<Application> {

    void create(Application application);

    Application getByCode(String code);

    void assignApplications(Integer tenantId, List<Integer> applicationIds);

    List<Application> listByTenant();
}
