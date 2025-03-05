package pers.ken.rt.auth.dto.req;

import lombok.Data;

/**
 * @ClassName: ApplicationCreateReq
 * @Created: 2024/12/11 17:43
 * @Author ken
 */
@Data
public class ApplicationCreateRequest {
    private String name;
    private String appCode;
    private String description;
    private String indexUrl;
}
