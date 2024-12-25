package pers.ken.rt.auth.controller.req;

import lombok.Data;

/**
 * @ClassName: ApplicationCreateReq
 * @Created: 2024/12/11 17:43
 * @Author ken
 */
@Data
public class ApplicationCreateReq {
    private String name;
    private String code;
    private String description;
    private String indexUrl;
}
