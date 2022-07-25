package pers.ken.rt.iam.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <code> UserPolicies </code>
 * <desc> UserPolicies </desc>
 * <b>Creation Time:</b> 2022/7/25 10:56.
 *
 * @author Ken.Hu
 */
@Data
@AllArgsConstructor
public class UserPoliciesResp {
    private List<Policy> policies;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Policy {
        private String subject;
        private String resource;
        private String action;
    }
}
