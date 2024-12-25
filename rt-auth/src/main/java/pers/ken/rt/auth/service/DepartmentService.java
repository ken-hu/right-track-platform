package pers.ken.rt.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.ken.rt.auth.repository.po.Department;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【department】的数据库操作Service
 * @createDate 2024-12-07 15:19:39
 */
public interface DepartmentService extends IService<Department> {

    List<List<Department>> listDepartmentsByUser();

    List<Department> listDepartments();
}
