package pers.ken.rt.auth.interfaces.dto.resp;

import lombok.Data;
import pers.ken.rt.auth.domain.entity.PolicyDocument;

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
    private PolicyDocument policyDocument;
}
