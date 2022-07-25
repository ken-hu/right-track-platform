package pers.ken.rt.common.iam;

import lombok.*;

import java.util.List;

/**
 * <code> UserAuthority </code>
 * <desc> UserAuthority </desc>
 * <b>Creation Time:</b> 2022/3/8 20:45.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail {
    private String userId;
    private String username;
    private List<PolicyDefine> policies;

    @Data
    public static class PolicyDefine {
        private String sub;
        private String obj;
        private String act;

        public PolicyDefine(List<String> policy) {
            this.sub = policy.get(1);
            this.obj = policy.get(2);
            this.act = policy.get(3);
        }
    }
}
