package pers.ken.rt.auth.dto.resp;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: GetUsersDetail
 * @Created: 2025/3/18 17:38
 * @Author ken
 */
@Data
@Builder
public class GetUserDetailResponse {
    private Integer userId;
    private String nickname;
    private String username;
    private String tenantCode;
    private Integer tenantId;
    private List<String> roles;
    private List<String> deptCodes;
}
