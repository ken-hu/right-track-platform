package pers.ken.rt.iam.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.ken.rt.iam.dto.req.UserPoliciesReq;
import pers.ken.rt.iam.dto.resp.UserPoliciesResp;

/**
 * <code> PolicyApi </code>
 * <desc> PolicyApi </desc>
 * <b>Creation Time:</b> 2022/7/25 13:41.
 *
 * @author Ken.Hu
 */
@FeignClient(
        value = "iam-service",
        contextId = "PolicyApi"
)
public interface PolicyApi {
    @PostMapping("/policies")
    UserPoliciesResp listPolicies(@PathVariable String userId, @RequestBody UserPoliciesReq userPoliciesReq);
}
