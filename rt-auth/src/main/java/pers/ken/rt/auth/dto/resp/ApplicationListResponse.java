package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: ApplicationListResp
 * @Created: 2024/12/11 17:46
 * @Author ken
 */
@Data
public class ApplicationListResponse {
    private Integer id;
    private String name;
    private String code;
}
