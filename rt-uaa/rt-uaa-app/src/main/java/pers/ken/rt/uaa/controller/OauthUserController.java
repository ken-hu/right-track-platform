package pers.ken.rt.uaa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.common.utils.BeanMapper;
import pers.ken.rt.uaa.dto.resp.UserResp;
import pers.ken.rt.uaa.entity.OauthUser;
import pers.ken.rt.uaa.service.UcUserService;

/**
 * <code> OauthUserController </code>
 * <desc> OauthUserController </desc>
 * <b>Creation Time:</b> 2021/10/19 0:08.
 *
 * @author _Ken.Hu
 */
@RestController
@AllArgsConstructor
@Tag(name = "Oauth")
public class OauthUserController {
    private UcUserService ucUserService;

    /**
     * User user resp.
     *
     * @param username the username
     * @return the user resp
     */
    @GetMapping("/user/{username}")
    public UserResp user(@RequestParam String username) {
        OauthUser oauthUser = ucUserService.getOauthUser(username);
        return BeanMapper.copyProperties(oauthUser, UserResp.class);
    }
}
