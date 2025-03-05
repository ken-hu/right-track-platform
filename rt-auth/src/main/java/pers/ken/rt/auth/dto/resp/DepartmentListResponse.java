package pers.ken.rt.auth.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: UserDepartmentsListResp
 * @Created: 2024/12/7 14:17
 * @Author ken
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentListResponse {
    private String code;
    private String parentCode;
    private String name;
    private List<DepartmentListResponse> children;
}
