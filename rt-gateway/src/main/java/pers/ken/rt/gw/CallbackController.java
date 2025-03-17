package pers.ken.rt.gw;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @ClassName: CallbackController
 * @Created: 2025/3/14 10:01
 * @Author ken
 */
@RestController
public class CallbackController {
    @GetMapping("/business-redirect")
    public void businessRedirect(ServerHttpResponse response) {
        // 该方法仅做URL跳转，不生成任何授权码
        String url = """
            http://www.ken.com:12306/oauth2/authorize\
            ?response_type=code\
            &client_id=channel\
            &redirect_uri=https%3A%2F%2Fwww.baidu.com\
            &scope=openid""";

        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create(url));
    }

    @GetMapping("/test-me")
    public Object test() {
        return ReactiveSecurityContextHolder.getContext()
            .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal);
    }
}
