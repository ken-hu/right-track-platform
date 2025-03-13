package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.mapper.DepartmentMapper;
import pers.ken.rt.auth.repository.po.Department;
import pers.ken.rt.auth.service.DepartmentService;
import pers.ken.rt.common.exception.BusinessVerificationException;
import pers.ken.rt.common.exception.ErrorCode;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【department】的数据库操作Service实现
 * @createDate 2024-12-07 15:19:39
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService {

    @Override
    public List<List<Department>> listDepartmentsByUser(Integer userId) {
        List<String> deptCodes = baseMapper.selectUserDeptCodes(userId);
        return deptCodes.stream().map(code -> baseMapper.selectRecursionUpDept(AccountContext.getTenantCode(), code)).toList();
    }

    @Override
    public List<Department> listDepartments() {
        String tenantCode = AccountContext.getTenantCode();
        if (null == tenantCode) {
            throw new BusinessVerificationException(ErrorCode.BUSINESS_ERROR, "Account tenantCode is null");
        }
        return baseMapper.selectList(Wrappers.lambdaQuery(Department.class).eq(Department::getTenantCode, tenantCode));
    }
}




