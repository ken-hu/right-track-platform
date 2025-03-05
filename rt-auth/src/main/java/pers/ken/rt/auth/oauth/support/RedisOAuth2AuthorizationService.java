package pers.ken.rt.auth.oauth.support;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

/**
 * @ClassName: OAuth2AuthorizationService
 * @Created: 2025/2/27 19:15
 * @Author ken
 */
@AllArgsConstructor
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RedisTemplate<String, OAuth2Authorization> redisTemplate;
    private static final String KEY_PREFIX = "oauth2:auth:";


    @Override
    public void save(OAuth2Authorization authorization) {
        String id = authorization.getId();
        String token = authorization.getAccessToken().getToken().getTokenValue();
        redisTemplate.opsForValue().set(KEY_PREFIX + id, authorization);
        redisTemplate.opsForValue().set(KEY_PREFIX + "access:" + token, authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        String id = authorization.getId();
        String token = authorization.getAccessToken().getToken().getTokenValue();
        redisTemplate.delete(KEY_PREFIX + id);
        redisTemplate.delete(KEY_PREFIX + "access:" + token);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return redisTemplate.opsForValue().get(KEY_PREFIX + id);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        if (tokenType == OAuth2TokenType.ACCESS_TOKEN) {
            return redisTemplate.opsForValue().get(KEY_PREFIX + "access:" + token);
        }
        return null;
    }
}
