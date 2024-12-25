package pers.ken.rt.auth.oauth.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName: Oauth2ThirdAccount
 * @Created: 2024/12/4 13:40
 * @Author ken
 */
@Data
public class Oauth2ThirdAccount {
    private String uniqueId;
    private String username;
    private String name;
    private String blog;
    private String avatarUrl;
    private String platform;
    /**
     * Third token
     */
    private String credentials;
    /**
     * Third token expireTime
     */
    private LocalDateTime credentialsExpiresAt;
    /**
     * CLIENT-DI
     */
    private String registrationId;


    private String sourceInfo;
}
