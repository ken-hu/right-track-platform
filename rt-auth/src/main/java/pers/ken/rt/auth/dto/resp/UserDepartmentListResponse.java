package pers.ken.rt.auth.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: UserDepartmentGetResp
 * @Created: 2024/12/25 14:41
 * @Author ken
 */
@Data
@Builder
public class UserDepartmentListResponse {
    private String code;
    private String name;
    private String parentCode;
    private String path;
}
