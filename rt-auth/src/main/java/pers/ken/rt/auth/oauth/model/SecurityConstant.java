package pers.ken.rt.auth.oauth.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @ClassName: SecurityCconstant
 * @Created: 2024/11/20 10:38
 * @Author ken
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    public static final String[] WHITE_LIST = new String[]{
        "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html",
        "/css/**", "/js/**", "/images/**", "/img/**", "/favicon.ico",
        "/error", "/resources/**", "/static/**",
    };


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Additional {
        public static final String TOKEN_TYPE = "token_type";
        public static final String NAME = "account_nickname";
        public static final String ACCOUNT_ID = "account_id";
        public static final String TENANT_CODE = "tenant_code";
        public static final String TENANT_ID = "tenant_id";
        public static final String STATUS = "account_status";
        public static final String ROLES = "roles";
    }
}
