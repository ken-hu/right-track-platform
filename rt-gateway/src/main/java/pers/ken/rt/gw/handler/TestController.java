package pers.ken.rt.gw.handler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @ClassName: TestController
 * @Created: 2024/11/20 15:10
 * @Author ken
 */
@RestController
public class TestController {
    @GetMapping("/v1/test-health")
    public String test() {
        return "health";
    }


    @GetMapping("/v1/test-user-info")
    public Principal me(Principal principal) {
        return principal;
    }
}
