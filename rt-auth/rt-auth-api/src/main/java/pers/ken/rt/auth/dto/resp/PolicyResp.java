package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: PolicyResp
 * @CreatedTime: 2023/1/9 15:07
 * @Desc:
 * @Author Ken
 */
@Data
public class PolicyResp {
    private String id;
    private String versionId;
    private String policyDocument;
}
