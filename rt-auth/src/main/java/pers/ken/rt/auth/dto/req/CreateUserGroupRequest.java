package pers.ken.rt.auth.dto.req;

import lombok.Data;

/**
 * @ClassName: UserGroupCreateRequest
 * @Created: 2025/1/6 17:19
 * @Author ken
 */
@Data
public class CreateUserGroupRequest {
    private String name;
    private String description;
}
