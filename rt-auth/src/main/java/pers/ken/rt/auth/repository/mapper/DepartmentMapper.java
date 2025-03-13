package pers.ken.rt.auth.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.ken.rt.auth.repository.po.Department;
import pers.ken.rt.starter.pbac.annotation.DataCondition;
import pers.ken.rt.starter.pbac.annotation.DataScope;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【department】的数据库操作Mapper
 * @createDate 2024-12-07 15:19:39
 * @Entity pers.ken.rt.auth.repository.po.Department
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> selectRecursionUpDept(@Param("tenantCode") String tenantCode,
                                           @Param("deptCode") String deptCode);

    @DataScope(table = "account", conditions = @DataCondition("id"))
    List<String> selectUserDeptCodes(@Param("userId") Integer userId);
}




