package pers.ken.rt.auth.oauth.support.third;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.repository.po.ThirdAccount;

import java.util.LinkedHashMap;

/**
 * @ClassName: FeishuUserConverterStrategy
 * @Created: 2025/3/13 17:13
 * @Author ken
 */
@Component("feishuUserConverterStrategy")
@Slf4j
public class FeishuUserConverterStrategy implements Oauth2UserConverterStrategy {
    @Override
    public ThirdAccount convert(OAuth2User oAuth2User) {
        // 获取三方用户信息
        LinkedHashMap<String, Object> dataMap = (LinkedHashMap<String, Object>) oAuth2User.getAttributes().get("data");
        log.info("Feishu account attribute:{}", oAuth2User.getAttributes());
        // 转换至Oauth2ThirdAccount
        ThirdAccount thirdAccount = new ThirdAccount();
        thirdAccount.setType("feishu");
        thirdAccount.setUniqueId(String.valueOf(dataMap.get("union_id")));
        thirdAccount.setUsername(String.valueOf(dataMap.get("name")));
        // 设置基础用户信息
        thirdAccount.setNickname(String.valueOf(dataMap.get("name")));
        thirdAccount.setAvatarUrl(String.valueOf(dataMap.get("avatar_url")));
        return thirdAccount;
    }
}
