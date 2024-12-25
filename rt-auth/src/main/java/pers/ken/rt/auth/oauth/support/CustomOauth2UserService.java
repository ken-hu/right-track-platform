package pers.ken.rt.auth.oauth.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pers.ken.rt.auth.oauth.support.third.ThirdUserConverterFactory;
import pers.ken.rt.auth.repository.po.ThirdAccount;
import pers.ken.rt.auth.service.ThirdAccountService;

import java.util.LinkedHashMap;

/**
 * @ClassName: CustomOauth2UserService
 * @Created: 2024/12/4 13:37
 * @Author ken
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final ThirdUserConverterFactory userConverterStrategy;
    private final ThirdAccountService thirdAccountService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 转为第三方登录的用户信息
        ThirdAccount oauth2ThirdAccount = userConverterStrategy.convert(userRequest, oAuth2User);
        log.info("CustomOauth2UserService oauth2ThirdAccount convert result:{}", oauth2ThirdAccount);
        thirdAccountService.checkAndSaved(oauth2ThirdAccount);
        // 将loginType设置至attributes中
        LinkedHashMap<String, Object> attributes = new LinkedHashMap<>(oAuth2User.getAttributes());
        // 将yml配置的RegistrationId当做登录类型
        attributes.put("loginType", userRequest.getClientRegistration().getRegistrationId());
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), attributes, userNameAttributeName);
    }

}
