package pers.ken.rt.auth.oauth.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import pers.ken.rt.auth.oauth.model.AuthUserDetails;
import pers.ken.rt.auth.oauth.model.SecurityConstant;
import pers.ken.rt.common.exception.BusinessVerificationException;
import pers.ken.rt.common.exception.ErrorCode;
import pers.ken.rt.common.utils.Jackson;

import java.util.*;

/**
 * @author Ken
 * @className: UserContext
 * @createdTime: 2023/2/27 22:02
 * @desc:
 */
@Data
@AllArgsConstructor
@Slf4j
public class AccountContext {

    public static Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static AuthUserDetails getUserDetails() {
        Object principal = getPrincipal();
        if (principal instanceof JwtAuthenticationToken token) {
            // todo accomplish me
            Map<String, Object> tokenAttributes = token.getTokenAttributes();
            return mapToUserDetails(tokenAttributes, token.getName());
        }
        if (principal instanceof OAuth2AuthenticationToken token) {
            // todo accomplish me
            OAuth2User oAuth2User = token.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            return mapToUserDetails(attributes, oAuth2User.getName());
        }
        // include  org.springframework.security.authentication.UsernamePasswordAuthenticationToken & pers.ken.rt.auth.oauth.support.password.PasswordAuthenticationToken
        if (principal instanceof AbstractAuthenticationToken token) {
            // todo accomplish me
        }
        if (principal instanceof AuthUserDetails authUserDetails) {
            return authUserDetails;
        }
        if (principal instanceof Jwt jwt) {
            Map<String, Object> claims = jwt.getClaims();
            return mapToUserDetails(claims, jwt.getSubject());
        }
        throw new BusinessVerificationException(ErrorCode.FAILED, "Unknown principal");
    }

    @SuppressWarnings("unchecked")
    private static AuthUserDetails mapToUserDetails(Map<String, Object> detailsMap, String username) {
        String accountIdStr = (String) detailsMap.get(SecurityConstant.Additional.ACCOUNT_ID);
        Integer accountId = Optional.ofNullable(accountIdStr).map(Integer::valueOf).orElse(null);
        String tenantCode = (String) detailsMap.get(SecurityConstant.Additional.TENANT_CODE);
        String tenantIdStr = (String) detailsMap.get(SecurityConstant.Additional.TENANT_ID);
        Integer tenantId = Optional.ofNullable(tenantIdStr).map(Integer::valueOf).orElse(null);
        List<String> roleList = (List<String>) detailsMap.get(SecurityConstant.Additional.ROLES);
        return new AuthUserDetails(accountId, username, tenantCode, tenantId, roleList);
    }

    public static Jwt getJwt() {
        Object principal = getPrincipal();
        if (!(principal instanceof Jwt)) {
            throw new ClassCastException("Principal is not Jwt");
        }
        return (Jwt) getPrincipal();
    }

    public static Map<String, Object> getPrincipalMap() {
        Object principal = getPrincipal();
        if (principal instanceof JwtAuthenticationToken token) {
            return token.getToken().getClaims();
        }
        if (principal instanceof OAuth2AuthenticationToken token) {
            return token.getPrincipal().getAttributes();
        }
        // include  org.springframework.security.authentication.UsernamePasswordAuthenticationToken & pers.ken.rt.auth.oauth.support.password.PasswordAuthenticationToken
        if (principal instanceof AbstractAuthenticationToken token) {
            return Jackson.fromJsonString(Jackson.toJsonString(token), new TypeReference<Map<String, Object>>() {
            });
        }
        if (principal instanceof AuthUserDetails userDetails) {
            return Jackson.fromJsonString(Jackson.toJsonString(userDetails), new TypeReference<Map<String, Object>>() {
            });
        }
        if (principal instanceof Jwt jwt) {
            return jwt.getClaims();
        }
        return Collections.emptyMap();
    }

    public static String getUsername() {
        AuthUserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }

    public static List<String> getRoles() {
        AuthUserDetails userDetails = getUserDetails();
        return userDetails.getRoles();
    }

    public static boolean isAdmin() {
        AuthUserDetails userDetails = getUserDetails();
        List<String> roles = userDetails.getRoles();
        return roles.contains("admin");
    }

    public static String getTenantCode() {
        AuthUserDetails userDetails = getUserDetails();
        return userDetails.getTenantCode();
    }

    public static Integer getTenantId() {
        AuthUserDetails userDetails = getUserDetails();
        return userDetails.getTenantId();
    }

    public static Integer getUserId() {
        AuthUserDetails userDetails = getUserDetails();
        return userDetails.getUserId();
    }

    public static <T> T get(Class<T> clazz) {
        Object principal = getPrincipal();
        String json = Jackson.toJsonString(principal);
        return Jackson.fromJsonString(json, clazz);
    }
}
