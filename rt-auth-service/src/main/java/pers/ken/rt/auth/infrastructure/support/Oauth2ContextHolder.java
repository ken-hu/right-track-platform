package pers.ken.rt.auth.infrastructure.support;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import pers.ken.rt.common.utils.Jackson;

import java.util.ArrayList;

/**
 * @author Ken
 * @className: Oauth2ContextHolder
 * @createdTime: 2023/2/27 21:50
 * @desc:
 */
public class Oauth2ContextHolder {
    private Oauth2ContextHolder() {
    }

    public static Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Jwt getJwt() {
        Object principal = getPrincipal();
        if (!(principal instanceof Jwt)) {
            throw new ClassCastException("Principal is not Jwt");
        }
        return (Jwt) getPrincipal();
    }

    public static UserContext getUserContext() {
        Object principal = getPrincipal();
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) getPrincipal();
            return new UserContext(1L, jwt.getSubject(), jwt.getAudience());
        }
        if (principal instanceof User) {
            User user = (User) getPrincipal();
            return new UserContext(1L, user.getUsername(), new ArrayList<>());
        }
        throw new IllegalArgumentException("Get userInfo failed");
    }

    public static String getUsername() {
        UserContext userContext = getUserContext();
        return userContext.getUsername();
    }

    public static Long getUserId() {
        UserContext userContext = getUserContext();
        return userContext.getUserId();
    }

    public static <T> T get(Class<T> clazz) {
        Object principal = getPrincipal();
        String json = Jackson.toJsonString(principal);
        return Jackson.fromJsonString(json, clazz);
    }
}
