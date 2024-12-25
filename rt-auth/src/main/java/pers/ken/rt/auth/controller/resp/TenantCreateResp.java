package pers.ken.rt.auth.controller.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: TenantCreateResp
 * @Created: 2024/12/7 16:13
 * @Author ken
 */
@Data
@AllArgsConstructor
public class TenantCreateResp {
    private Integer id;
    private String name;
}
