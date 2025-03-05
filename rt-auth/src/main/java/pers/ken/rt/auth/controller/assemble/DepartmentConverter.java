package pers.ken.rt.auth.controller.assemble;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.auth.dto.resp.DepartmentListResponse;
import pers.ken.rt.auth.dto.resp.UserDepartmentListResponse;
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

    DepartmentListResponse convert(Department department);

    default List<DepartmentListResponse> toTreeNodes(List<Department> departments) {
        return TreeUtils.buildTree(departments, DepartmentConverter.INSTANCE::convert,
            DepartmentListResponse::getCode,
            DepartmentListResponse::getParentCode,
            DepartmentListResponse::getChildren,
            DepartmentListResponse::setChildren,
            departmentListResponse -> "5606".equals(departmentListResponse.getParentCode()));
    }

    default List<UserDepartmentListResponse> convert(List<List<Department>> multiDepartments) {
        return multiDepartments.stream().map(
                        departments -> {
                            if (!CollectionUtils.isEmpty(departments)) {
                                Department department = departments.get(departments.size() - 1);
                                String deptPath = StringUtils.join(departments.stream().map(Department::getName).toList(), "/");
                                return UserDepartmentListResponse.builder()
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
