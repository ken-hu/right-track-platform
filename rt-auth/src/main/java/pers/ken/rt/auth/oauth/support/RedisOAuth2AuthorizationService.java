package pers.ken.rt.auth.oauth.support;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName: OAuth2AuthorizationService
 * @Created: 2025/2/27 19:15
 * @Author ken
 */
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String AUTH_KEY_PREFIX = "rt-auth:oauth2:authorization:";
    private static final long DEFAULT_MAX_TTL_SECONDS = 300;
    private static final List<String> ALL_KEY_INDEX = List.of(
        OAuth2ParameterNames.CODE,
        OAuth2ParameterNames.STATE,
        OAuth2TokenType.ACCESS_TOKEN.getValue(),
        OAuth2TokenType.REFRESH_TOKEN.getValue(),
        OidcParameterNames.ID_TOKEN,
        OAuth2ParameterNames.USER_CODE, OAuth2ParameterNames.DEVICE_CODE
    );


    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        Boolean invalidated = Optional.ofNullable(authorization.getRefreshToken())
            .map(x -> (Boolean) x.getMetadata().get(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME))
            .orElse(false);
        if (invalidated) {
            // 标记token被吊销，删除redis的REFRESH_TOKEN
            remove(authorization);
        }
        String authId = authorization.getId();

        long maxTtl = DEFAULT_MAX_TTL_SECONDS;
        // AuthCode信息
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        if (null != authorizationCode) {
            long tokenTtl = getTtl(authorizationCode);
            redisTemplate.opsForValue().set(getTokenIndexKey(OAuth2ParameterNames.CODE, authorizationCode.getToken().getTokenValue()), authId, tokenTtl, TimeUnit.SECONDS);
            maxTtl = recordMaxTtl(maxTtl, tokenTtl);
        }

        // state一般和auth_code保持一致
        String authorizationState = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (StringUtils.hasText(authorizationState)) {
            long tokenTtl = getTtl(authorizationCode);
            redisTemplate.opsForValue().set(getTokenIndexKey(OAuth2ParameterNames.STATE, authorizationState), authId, tokenTtl, TimeUnit.SECONDS);
            maxTtl = recordMaxTtl(maxTtl, tokenTtl);
        }

        // AccessToken信息
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (null != accessToken) {
            long tokenTtl = getTtl(accessToken);
            redisTemplate.opsForValue().set(getTokenIndexKey(OAuth2TokenType.ACCESS_TOKEN.getValue(), accessToken.getToken().getTokenValue()), authId, tokenTtl, TimeUnit.SECONDS);
            maxTtl = recordMaxTtl(maxTtl, tokenTtl);
        }

        // RefreshToken信息
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (null != refreshToken) {
            long tokenTtl = getTtl(refreshToken);
            redisTemplate.opsForValue().set(getTokenIndexKey(OAuth2TokenType.REFRESH_TOKEN.getValue(), refreshToken.getToken().getTokenValue()), authId, tokenTtl, TimeUnit.SECONDS);
            maxTtl = recordMaxTtl(maxTtl, tokenTtl);

        }

        // IdToken信息
        OAuth2Authorization.Token<OidcIdToken> oidcIdTokenToken = authorization.getToken(OidcIdToken.class);
        if (null != oidcIdTokenToken) {
            long tokenTtl = getTtl(oidcIdTokenToken);
            redisTemplate.opsForValue().set(getTokenIndexKey(OidcParameterNames.ID_TOKEN, oidcIdTokenToken.getToken().getTokenValue()), authId, tokenTtl, TimeUnit.SECONDS);
            maxTtl = recordMaxTtl(maxTtl, tokenTtl);

        }

        OAuth2Authorization.Token<OAuth2UserCode> userCodeToken = authorization.getToken(OAuth2UserCode.class);
        if (null != userCodeToken) {
            long tokenTtl = getTtl(userCodeToken);
            redisTemplate.opsForValue().set(getTokenIndexKey(OAuth2ParameterNames.USER_CODE, userCodeToken.getToken().getTokenValue()), authId, tokenTtl, TimeUnit.SECONDS);
            maxTtl = recordMaxTtl(maxTtl, tokenTtl);

        }

        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCodeToken = authorization.getToken(OAuth2DeviceCode.class);
        if (null != deviceCodeToken) {
            long tokenTtl = getTtl(deviceCodeToken);
            redisTemplate.opsForValue().set(getTokenIndexKey(OAuth2ParameterNames.DEVICE_CODE, deviceCodeToken.getToken().getTokenValue()), authId, tokenTtl, TimeUnit.SECONDS);
            maxTtl = recordMaxTtl(maxTtl, tokenTtl);
        }

        // 认证信息 需要设置最长时间的token的ttl
        redisTemplate.opsForValue().set(AUTH_KEY_PREFIX + authId, authorization, maxTtl, TimeUnit.SECONDS);
    }

    private long recordMaxTtl(long maxTtl, long tokenTtl) {
        if (tokenTtl > maxTtl) {
            return tokenTtl;
        }
        return maxTtl;
    }

    private String getTokenIndexKey(String tokenType, String tokenValue) {
        return AUTH_KEY_PREFIX + tokenType + ":" + tokenValue;
    }


    private long getTtl(OAuth2Authorization.Token<?> token) {
        if (null != token) {
            Instant expiresAt = token.getToken().getExpiresAt();
            long seconds = Duration.between(Instant.now(), expiresAt).getSeconds();
            if (seconds > 0) {
                return seconds;
            }
        }
        return DEFAULT_MAX_TTL_SECONDS;
    }


    private String getTokenKey(@NotNull String tokenValue, OAuth2TokenType tokenType) {
        return AUTH_KEY_PREFIX + Optional.ofNullable(tokenType).map(OAuth2TokenType::getValue).orElse(OAuth2TokenType.REFRESH_TOKEN.getValue()) + ":" + tokenValue;
    }


    @Override
    public void remove(OAuth2Authorization authorization) {
        // 认证信息
        String authId = authorization.getId();
        redisTemplate.delete(AUTH_KEY_PREFIX + authId);
        // AccessToken信息
        if (null != authorization.getAccessToken()) {
            redisTemplate.delete(getTokenIndexKey(OAuth2TokenType.ACCESS_TOKEN.getValue(), authorization.getAccessToken().getToken().getTokenValue()));
        }
        // RefreshToken信息
        if (null != authorization.getRefreshToken()) {
            redisTemplate.delete(getTokenIndexKey(OAuth2TokenType.REFRESH_TOKEN.getValue(), authorization.getRefreshToken().getToken().getTokenValue()));
        }
        // AuthCode信息
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        if (null != authorizationCode) {
            redisTemplate.delete(getTokenIndexKey(OAuth2ParameterNames.CODE, authorizationCode.getToken().getTokenValue()));
        }

        // state
        String authorizationState = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (StringUtils.hasText(authorizationState)) {
            redisTemplate.delete(getTokenIndexKey(OAuth2ParameterNames.STATE, authorizationState));
        }
        // IdToken信息
        OAuth2Authorization.Token<OidcIdToken> oidcIdTokenToken = authorization.getToken(OidcIdToken.class);
        if (null != oidcIdTokenToken) {
            redisTemplate.delete(getTokenIndexKey(OidcParameterNames.ID_TOKEN, oidcIdTokenToken.getToken().getTokenValue()));
        }

        OAuth2Authorization.Token<OAuth2UserCode> userCodeToken = authorization.getToken(OAuth2UserCode.class);
        if (null != userCodeToken) {
            redisTemplate.delete(getTokenIndexKey(OAuth2ParameterNames.USER_CODE, userCodeToken.getToken().getTokenValue()));
        }

        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCodeToken = authorization.getToken(OAuth2DeviceCode.class);
        if (null != deviceCodeToken) {
            redisTemplate.delete(getTokenIndexKey(OAuth2ParameterNames.DEVICE_CODE, deviceCodeToken.getToken().getTokenValue()));
        }
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return (OAuth2Authorization) redisTemplate.opsForValue().get(AUTH_KEY_PREFIX + id);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        List<String> allRedisKey = ALL_KEY_INDEX.stream().map(keyType -> getTokenIndexKey(keyType, token)).collect(Collectors.toList());
        String tokenKey = getTokenKey(token, tokenType);
        // 根据预设的indexTokenType 找到所有的 key 一个个找到相关的 authId (因为有tokenType = NULL的情况，参考Jdbc做模糊查询)
        List<Object> allAuthIds = redisTemplate.opsForValue().multiGet(allRedisKey);
        if (CollectionUtils.isEmpty(allAuthIds)) {
            return null;
        }
        String authId = allAuthIds.stream().filter(Objects::nonNull).map(x -> (String) x).findFirst().orElse(null);
        return authId != null ? findById(authId) : null;
    }
}
