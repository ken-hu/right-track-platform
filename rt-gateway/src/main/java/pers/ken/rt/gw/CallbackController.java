package pers.ken.rt.gw;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/exchange-token")
//    public ResponseEntity<String> exchangeToken(@RequestParam String code, HttpServletRequest request) {
//
//        // 1. 构造授权请求
//        OAuth2AuthorizationCodeAuthenticationToken authenticationToken =
//            new OAuth2AuthorizationCodeAuthenticationToken(
//                "my-client",  // 客户端注册的 registrationId
//                URI.create("http://auth-server:8080"),  // 认证中心地址
//                code,
//                URI.create(request.getRequestURL().toString())  // 当前请求的回调地址
//            );
//
//        // 2. 换取 Token
//        OAuth2AuthorizedClient authorizedClient =
//            authorizedClientManager.authorize(authenticationToken);
//
//        // 3. 获取 Token 信息
//        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
//        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();
//
//        // 4. 返回结果（示例）
//        return ResponseEntity.ok("Access Token: " + accessToken.getTokenValue());
//    }
}
