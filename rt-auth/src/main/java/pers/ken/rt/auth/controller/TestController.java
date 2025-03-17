package pers.ken.rt.auth.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Created: 2025/3/14 23:22
 * @Author ken
 */
@RestController
public class TestController {

    @GetMapping("/session/set")
    public String setSession(HttpSession session) {
        session.setAttribute("user", "KenHu");
        return "Session set for user: KenHu";
    }

    @GetMapping("/session/get")
    public String getSession(HttpSession session) {
        return "User from session: " + session.getAttribute("user");
    }
}
