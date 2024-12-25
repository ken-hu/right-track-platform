package pers.ken.rt.auth.oauth.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.ken.rt.auth.oauth.support.third.ThirdUserConverterFactory;
import pers.ken.rt.auth.repository.po.ThirdAccount;
import pers.ken.rt.auth.service.ThirdAccountService;

import java.util.LinkedHashMap;

/**
 * @ClassName: CustomOidcUserService
 * @Created: 2024/12/4 14:00
 * @Author ken
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOidcUserService extends OidcUserService {
    private final ThirdUserConverterFactory userConverterStrategy;
    private final ThirdAccountService thirdAccountService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // 获取三方用户信息
        OidcUser oidcUser = super.loadUser(userRequest);
        // 转为项目中的三方用户信息
        ThirdAccount oauth2ThirdAccount = userConverterStrategy.convert(userRequest, oidcUser);
        log.info("CustomOidcUserService oauth2ThirdAccount convert result:{}", oauth2ThirdAccount);

        // 检查用户信息
        thirdAccountService.checkAndSaved(oauth2ThirdAccount);
        OidcIdToken oidcIdToken = oidcUser.getIdToken();
        // 将loginType设置至attributes中
        LinkedHashMap<String, Object> attributes = new LinkedHashMap<>(oidcIdToken.getClaims());
        // 将RegistrationId当做登录类型
        attributes.put("loginType", userRequest.getClientRegistration().getRegistrationId());
        // 重新生成一个idToken
        OidcIdToken idToken = new OidcIdToken(oidcIdToken.getTokenValue(), oidcIdToken.getIssuedAt(), oidcIdToken.getExpiresAt(), attributes);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        // 重新生成oidcUser
        if (StringUtils.hasText(userNameAttributeName)) {
            return new DefaultOidcUser(oidcUser.getAuthorities(), idToken, oidcUser.getUserInfo(), userNameAttributeName);
        }
        return new DefaultOidcUser(oidcUser.getAuthorities(), idToken, oidcUser.getUserInfo());
    }

}
