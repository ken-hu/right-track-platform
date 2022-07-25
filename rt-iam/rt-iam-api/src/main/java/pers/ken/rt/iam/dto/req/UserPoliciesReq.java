package pers.ken.rt.iam.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <code> UserPoliciesReq </code>
 * <desc> UserPoliciesReq </desc>
 * <b>Creation Time:</b> 2022/7/25 11:14.
 *
 * @author Ken.Hu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPoliciesReq {
    private String resource;
    private String action;
}
