package pers.ken.rt.auth.interfaces.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: PolicyResp
 * @CreatedTime: 2023/1/9 15:07
 * @Desc:
 * @Author Ken
 */
@Data
@Builder
public class PolicyItemResp {
    private Long id;
    private String versionId;
    private String name;
}
