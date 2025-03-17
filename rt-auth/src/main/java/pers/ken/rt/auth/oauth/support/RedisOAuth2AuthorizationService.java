package pers.ken.rt.auth.oauth.support;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: OAuth2AuthorizationService
 * @Created: 2025/2/27 19:15
 * @Author ken
 */
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String AUTH_KEY_PREFIX = "oauth2:authorization:";
    private final long DEFAULT_MAX_TTL_SECONDS = 1800;



    @Override
    public void save(OAuth2Authorization authorization) {
        if (null != authorization) {
            long ttl = getTtl(authorization.getAccessToken());
            // 认证信息
            String authId = authorization.getId();
            redisTemplate.opsForValue().set(AUTH_KEY_PREFIX + authId, authorization, ttl, TimeUnit.SECONDS);
//            String jsonString = Jackson.toJsonString(authorization);
//            System.out.println(jsonString);
//            redisTemplate.opsForValue().set(AUTH_KEY_PREFIX + authId, jsonString, ttl, TimeUnit.SECONDS);

            // AccessToken信息
            if (null != authorization.getAccessToken()) {
                String tokenKey = getTokenKey(authorization.getAccessToken().getToken().getTokenValue(), OAuth2TokenType.ACCESS_TOKEN);
                redisTemplate.opsForValue().set(tokenKey, authId, ttl, TimeUnit.SECONDS);
            }
            // RefreshToken信息
            if (null != authorization.getRefreshToken()) {
                String tokenKey = getTokenKey(authorization.getRefreshToken().getToken().getTokenValue(), OAuth2TokenType.REFRESH_TOKEN);
                redisTemplate.opsForValue().set(tokenKey, authId, getTtl(authorization.getRefreshToken()), TimeUnit.SECONDS);
            }

            // AuthCode信息
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
            if (null != authorizationCode) {
                String tokenKey = AUTH_KEY_PREFIX + "code:" + authorizationCode.getToken().getTokenValue();
                redisTemplate.opsForValue().set(tokenKey, authId, getTtl(authorizationCode), TimeUnit.SECONDS);
            }
        }
    }

    private long getTtl(OAuth2Authorization.Token<?> token) {
        if (null != token) {
            Instant expiresAt = token.getToken().getExpiresAt();
            return Duration.between(Instant.now(), expiresAt).getSeconds();
        } else {
            return DEFAULT_MAX_TTL_SECONDS;
        }
    }


    private String getTokenKey(@NotNull String tokenValue, OAuth2TokenType tokenType) {
        return AUTH_KEY_PREFIX + tokenType.getValue() + ":" + tokenValue;
    }


    @Override
    public void remove(OAuth2Authorization authorization) {
        // 认证信息
        String authId = authorization.getId();
        redisTemplate.delete(AUTH_KEY_PREFIX + authId);
        // AccessToken信息
        if (null != authorization.getAccessToken()) {
            String tokenKey = getTokenKey(authorization.getAccessToken().getToken().getTokenValue(), OAuth2TokenType.ACCESS_TOKEN);
            redisTemplate.delete(tokenKey);
        }
        // RefreshToken信息
        if (null != authorization.getRefreshToken()) {
            String tokenKey = getTokenKey(authorization.getRefreshToken().getToken().getTokenValue(), OAuth2TokenType.REFRESH_TOKEN);
            redisTemplate.delete(tokenKey);
        }
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return (OAuth2Authorization) redisTemplate.opsForValue().get(AUTH_KEY_PREFIX + id);
//        RedisOauthAuthorization redisOauthAuthorization = (RedisOauthAuthorization) redisTemplate.opsForValue().get(AUTH_KEY_PREFIX + id);
//        if (null != redisOauthAuthorization) {
//            return redisOauthAuthorization.toOAuth2Authorization();
//        }

//        String json = (String)redisTemplate.opsForValue().get(AUTH_KEY_PREFIX + id);
//        return Jackson.fromJsonString(json, OAuth2Authorization.class);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        String tokenKey = getTokenKey(token, tokenType);
        String authId = (String) redisTemplate.opsForValue().get(tokenKey);
        return authId != null ? findById(authId) : null;
    }
}
