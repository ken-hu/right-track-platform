package pers.ken.rt.auth.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.ken.rt.auth.api.fallback.OauthUserApiFallbackFactory;
import pers.ken.rt.auth.dto.resp.UserResp;

/**
 * <code> OauthUserApi </code>
 * <desc> OauthUserApi </desc>
 * <b>Creation Time:</b> 2021/10/18 23:54.
 *
 * @author _Ken.Hu
 */
@FeignClient(
        value = "usercenter",
        contextId = "OauthUserApi",
        fallbackFactory = OauthUserApiFallbackFactory.class
)
public interface OauthUserApi {
    @GetMapping("/user/{username}")
    UserResp user(@PathVariable String username);
}
