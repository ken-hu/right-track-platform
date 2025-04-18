package pers.ken.rt.auth.oauth.support.third;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.repository.po.ThirdAccount;

import java.util.Map;

/**
 * @ClassName: GithubUserConverter
 * @Created: 2024/12/5 20:14
 * @Author ken
 */
@Component("githubUserConverterStrategy")
@Slf4j
public class GithubUserConverterStrategy implements Oauth2UserConverterStrategy {
    @Override
    public ThirdAccount convert(OAuth2User oAuth2User) {
        // 获取三方用户信息
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Github account attribute:{}", oAuth2User.getAttributes());
        // 转换至Oauth2ThirdAccount
        ThirdAccount thirdAccount = new ThirdAccount();
        thirdAccount.setUniqueId(oAuth2User.getName());
        thirdAccount.setUsername(String.valueOf(attributes.get("login")));
        thirdAccount.setType("github");
        thirdAccount.setNickname(String.valueOf(attributes.get("name")));
        thirdAccount.setAvatarUrl(String.valueOf(attributes.get("avatar_url")));
        return thirdAccount;
    }
}
