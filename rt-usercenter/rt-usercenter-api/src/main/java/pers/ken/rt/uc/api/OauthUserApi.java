package pers.ken.rt.uc.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.ken.rt.uc.api.fallback.OauthUserApiFallbackFactory;
import pers.ken.rt.uc.dto.UserDTO;

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
    UserDTO user(@PathVariable String username);
}
