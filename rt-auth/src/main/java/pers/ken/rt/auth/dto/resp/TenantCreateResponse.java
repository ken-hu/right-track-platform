package pers.ken.rt.auth.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: TenantCreateResp
 * @Created: 2024/12/7 16:13
 * @Author ken
 */
@Data
@AllArgsConstructor
public class TenantCreateResponse {
    private Integer id;
    private String name;
}
