package pers.ken.rt.uc.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * <name> OauthController </name>
 * <desc> </desc>
 * Creation Time: 2021/9/29 0:51.
 *
 * @author _Ken.Hu
 */
@AllArgsConstructor
@RestController
@RequestMapping
public class OauthController {
    private TokenEndpoint tokenEndpoint;
    private final KeyPair keyPair;

    @PostMapping("/oauth/token")
    public OAuth2AccessToken getToken(@ApiIgnore Principal principal,
                                      @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> oauth2AccessToken =
                tokenEndpoint.postAccessToken(principal, parameters);
        return oauth2AccessToken.getBody();
    }

    @GetMapping("/oauth/rsa/getPublicKey")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
