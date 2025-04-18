package pers.ken.rt.auth.oauth.support.third;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.repository.po.ThirdAccount;

import java.util.Map;

/**
 * @ClassName: GiteeUserConverter
 * @Created: 2024/12/4 13:44
 * @Author ken
 */
@Component("giteeUserConverterStrategy")
@Slf4j
public class GiteeUserConverterStrategy implements Oauth2UserConverterStrategy {
    @Override
    public ThirdAccount convert(OAuth2User oAuth2User) {
        // 获取三方用户信息
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Gitee account attribute:{}", oAuth2User.getAttributes());
        // 转换至Oauth2ThirdAccount
        ThirdAccount thirdAccount = new ThirdAccount();
        thirdAccount.setUniqueId(oAuth2User.getName());
        thirdAccount.setUsername(String.valueOf(attributes.get("login")));
        thirdAccount.setType("gitee");
//        thirdAccount.setBlog(String.valueOf(attributes.get("blog")));
        // 设置基础用户信息
        thirdAccount.setNickname(String.valueOf(attributes.get("name")));
        thirdAccount.setAvatarUrl(String.valueOf(attributes.get("avatar_url")));
        return thirdAccount;
    }
}
