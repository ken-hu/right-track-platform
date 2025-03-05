package pers.ken.rt.auth.dto.req;

import lombok.Data;

/**
 * @ClassName: UserGroupUpdateRequest
 * @Created: 2025/1/6 17:25
 * @Author ken
 */
@Data
public class UserGroupUpdateRequest {
    private String name;
    private String description;
}
