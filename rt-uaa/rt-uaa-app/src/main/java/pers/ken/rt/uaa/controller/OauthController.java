package pers.ken.rt.uaa.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "Oauth")
public class OauthController {
    private TokenEndpoint tokenEndpoint;
    private final KeyPair keyPair;

    @PostMapping(value = "/oauth/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public OAuth2AccessToken getToken(Principal principal,
                                      @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> oauth2AccessToken =
                tokenEndpoint.postAccessToken(principal, parameters);
        return oauth2AccessToken.getBody();
    }

    @GetMapping(value = "/oauth/rsa/getPublicKey", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
