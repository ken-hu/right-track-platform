package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.controller.assemble.DepartmentConverter;
import pers.ken.rt.auth.dto.resp.DepartmentListResponse;
import pers.ken.rt.auth.dto.resp.UserDepartmentListResponse;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.po.Department;
import pers.ken.rt.auth.service.DepartmentService;
import pers.ken.rt.common.model.TreeResponse;

import java.util.List;

/**
 * @ClassName: DepartmentController
 * @Created: 2024/12/6 17:47
 * @Author ken
 */
@Tag(name = "department", description = "部门/组织架构")
@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(summary = "我的部门信息")
    @GetMapping("/v1/departments/me")
    public List<UserDepartmentListResponse> listDepartmentsByUser() {
        List<List<Department>> multiDepartments = departmentService.listDepartmentsByUser(AccountContext.getUserId());
        return DepartmentConverter.INSTANCE.convert(multiDepartments);
    }

    @Operation(summary = "部门列表/树")
    @GetMapping("/v1/departments")
    public TreeResponse<DepartmentListResponse> listDepartments() {
        List<Department> departments = departmentService.listDepartments();
        return TreeResponse.of(DepartmentConverter.INSTANCE.toTreeNodes(departments));
    }
}
