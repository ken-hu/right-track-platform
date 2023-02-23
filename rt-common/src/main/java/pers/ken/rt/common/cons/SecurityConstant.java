package pers.ken.rt.common.cons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Name: SecurityCons
 * Create Time: 2023/1/7 17:29.
 *
 * @author Ken
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {
    public static final String CUSTOM_TOKEN = "test_token";
    public static final String BEARER = "Bearer ";
    public static final String USER_INFO = "X-User-Info";
    public static final String USER_NAME = "user_name";
    public static String TOKEN_TYPE = "token_type";
}
