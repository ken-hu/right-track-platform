package pers.ken.rt.uc.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <name> TestController </name>
 * <desc> TestController </desc>
 * Creation Time: 2021/10/7 18:23.
 *
 * @author _Ken.Hu
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Success";
    }
}
