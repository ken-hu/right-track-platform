package pers.ken.rt.auth.dto.req;

import lombok.Data;

/**
 * @ClassName: RoleCreateRequest
 * @Created: 2025/1/6 17:08
 * @Author ken
 */
@Data
public class RoleCreateRequest {
    private String name;

    private String description;
}
