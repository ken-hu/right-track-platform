package pers.ken.rt.auth.oauth.support.third;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.repository.po.ThirdAccount;

import java.util.Map;

/**
 * @ClassName: DefaultUserConverterStrategy
 * @Created: 2025/3/19 15:19
 * @Author ken
 */
@Component("defaultUserConverterStrategy")
@Slf4j
public class DefaultUserConverterStrategy implements Oauth2UserConverterStrategy {
    @Override
    public ThirdAccount convert(OAuth2User oAuth2User) {
        // 获取三方用户信息
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Datastory account attribute:{}", oAuth2User.getAttributes());
        // 转换至Oauth2ThirdAccount
        ThirdAccount thirdAccount = new ThirdAccount();
        thirdAccount.setType("miniso");
        thirdAccount.setUniqueId(String.valueOf(attributes.get("userName")));
        thirdAccount.setUsername(String.valueOf(attributes.get("userName")));
        // 设置基础用户信息
        thirdAccount.setNickname(String.valueOf(attributes.get("nickName")));
        return thirdAccount;
    }
}
