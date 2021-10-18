package pers.ken.rt.uc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.common.utils.BeanMapper;
import pers.ken.rt.uc.dto.UserDTO;
import pers.ken.rt.uc.entity.OauthUser;
import pers.ken.rt.uc.service.UcUserService;

/**
 * <code> OauthUserController </code>
 * <desc> OauthUserController </desc>
 * <b>Creation Time:</b> 2021/10/19 0:08.
 *
 * @author _Ken.Hu
 */
@RestController
public class OauthUserController {
    private UcUserService ucUserService;
    @GetMapping("/user/{username}")
    public UserDTO user(@PathVariable String username){
        OauthUser oauthUser = ucUserService.getOauthUser(username);
        return BeanMapper.copyProperties(oauthUser, UserDTO.class);
    }
}
