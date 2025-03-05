package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: RoleGetResponse
 * @Created: 2025/1/6 17:08
 * @Author ken
 */
@Data
public class RoleGetResponse {
    private String id;
    private String name;
    private String description;
}
