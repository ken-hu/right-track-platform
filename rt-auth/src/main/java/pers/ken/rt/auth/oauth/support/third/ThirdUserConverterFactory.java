package pers.ken.rt.auth.oauth.support.third;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.oauth.model.Oauth2ThirdAccount;
import pers.ken.rt.auth.repository.po.ThirdAccount;
import pers.ken.rt.common.utils.Jackson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * @ClassName: ThirdUserConverter
 * @Created: 2024/12/4 14:04
 * @Author ken
 */
@Component
@RequiredArgsConstructor
public class ThirdUserConverterFactory {
    /**
     * spring 自动注入到该map
     */
    private final Map<String, Oauth2UserConverterStrategy> oauth2UserConverterMap;

    public Oauth2UserConverterStrategy getConverterInstance(String loginType) {
        if (StringUtils.isBlank(loginType)) {
            throw new UnsupportedOperationException("登录方式不能为空.");
        }
        Oauth2UserConverterStrategy userConverterStrategy = oauth2UserConverterMap.get(loginType + "UserConverterStrategy");
        if (userConverterStrategy == null) {
            throw new UnsupportedOperationException("不支持[" + loginType + "]登录方式获取用户信息转换器");
        }
        return userConverterStrategy;
    }

    /**
     * 根据登录方式获取转换器实例，使用转换器获取用户信息
     *
     * @param userRequest 获取三方用户信息入参
     * @param oAuth2User  三方登录获取到的认证信息
     * @return {@link Oauth2ThirdAccount}
     */
    public ThirdAccount convert(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // 获取三方登录配置的registrationId，这里将他当做登录方式
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 转换用户信息
        Oauth2UserConverterStrategy userConverter = getConverterInstance(registrationId);
        ThirdAccount oauth2ThirdAccount = userConverter.convert(oAuth2User);
        // 保留原始信息
        oauth2ThirdAccount.setExt(Jackson.toJsonString(oAuth2User.getAttributes()));

        // 获取AccessToken
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        // 设置token
        oauth2ThirdAccount.setCredentials(accessToken.getTokenValue());
        // 设置账号的方式
        oauth2ThirdAccount.setRegistrationId(registrationId);
        Instant expiresAt = accessToken.getExpiresAt();
        if (expiresAt != null) {
            LocalDateTime tokenExpiresAt = expiresAt.atZone(ZoneId.of("UTC")).toLocalDateTime();
            // token过期时间
            oauth2ThirdAccount.setCredentialsExpiresAt(tokenExpiresAt);
        }
        return oauth2ThirdAccount;
    }
}
