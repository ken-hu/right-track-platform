package pers.ken.rt.auth.oauth.support.third;

import org.springframework.security.oauth2.core.user.OAuth2User;
import pers.ken.rt.auth.oauth.model.Oauth2ThirdAccount;
import pers.ken.rt.auth.repository.po.ThirdAccount;

/**
 * @ClassName: Oauth2UserConverterStrategy
 * @Created: 2024/12/4 13:38
 * @Author ken
 */
public interface Oauth2UserConverterStrategy {
    /**
     * 将oauth2登录的认证信息转为 {@link Oauth2ThirdAccount}
     *
     * @param oAuth2User oauth2登录获取的用户信息
     * @return 项目中的用户信息
     */
    ThirdAccount convert(OAuth2User oAuth2User);
}
