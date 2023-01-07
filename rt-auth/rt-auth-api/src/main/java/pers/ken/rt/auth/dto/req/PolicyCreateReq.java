package pers.ken.rt.auth.dto.req;

import lombok.Data;

/**
 * Name: PolicyCreateReq
 * Creation Time: 2023/1/7 23:35.
 *
 * @author Ken
 */
@Data
public class PolicyCreateReq {
    private Long id;
    private String version;

    private String policyDocument;
}
