package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: ApplicationCreateResponse
 * @Created: 2025/1/2 18:46
 * @Author ken
 */
@Data
public class ApplicationCreateResponse {
    private Integer id;

    private String appCode;

    private String name;

    private String description;

    private String indexUrl;
}
