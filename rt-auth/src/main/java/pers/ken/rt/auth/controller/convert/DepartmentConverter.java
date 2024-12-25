package pers.ken.rt.auth.controller.convert;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.auth.controller.resp.DepartmentsListResp;
import pers.ken.rt.auth.controller.resp.UserDepartmentListResp;
import pers.ken.rt.auth.repository.po.Department;
import pers.ken.rt.common.utils.TreeUtils;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName: DepartmentAssembler
 * @Created: 2024/12/12 14:27
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentConverter {
    DepartmentConverter INSTANCE = Mappers.getMapper(DepartmentConverter.class);

    DepartmentsListResp convert(Department department);

    default List<DepartmentsListResp> toTreeNodes(List<Department> departments) {
        return TreeUtils.buildTree(departments, DepartmentConverter.INSTANCE::convert,
                DepartmentsListResp::getCode,
                DepartmentsListResp::getParentCode,
                DepartmentsListResp::getChildren,
                DepartmentsListResp::setChildren,
                departmentsListResp -> "5606".equals(departmentsListResp.getParentCode()));
    }

    default List<UserDepartmentListResp> convert(List<List<Department>> multiDepartments) {
        return multiDepartments.stream().map(
                        departments -> {
                            if (!CollectionUtils.isEmpty(departments)) {
                                Department department = departments.get(departments.size() - 1);
                                String deptPath = StringUtils.join(departments.stream().map(Department::getName).toList(), "/");
                                return UserDepartmentListResp.builder()
                                        .name(department.getName())
                                        .code(department.getCode())
                                        .parentCode(department.getParentCode())
                                        .path(deptPath)
                                        .build();
                            }
                            return null;
                        }
                ).filter(Objects::nonNull)
                .toList();
    }
}
