package pers.ken.rt.gw.oauth.support;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import pers.ken.rt.gw.cons.SecurityConstant;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ken
 * @className: AnonymousOauth2User
 * @createdTime: 2023/3/31 14:06
 * @desc:
 */
public class AnonymousOauth2User {
    private AnonymousOauth2User() {
    }

    public static DefaultOAuth2User defaultUser() {
        return new DefaultOAuth2User(
            Collections.emptyList(),
            Map.of(SecurityConstant.USER_NAME, SecurityConstant.ANONYMOUS_USER),
            SecurityConstant.USER_NAME);
    }

    public static boolean isAnonymous(DefaultOAuth2User oAuth2User) {
        Object anonymous = oAuth2User.getAttribute(SecurityConstant.USER_NAME);
        if (Objects.nonNull(anonymous) && SecurityConstant.ANONYMOUS_USER.equals(anonymous)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
