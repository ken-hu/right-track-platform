package pers.ken.rt.auth.oauth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import pers.ken.rt.auth.oauth.model.AuthUserDetails;
import pers.ken.rt.auth.oauth.model.SecurityConstant;
import pers.ken.rt.auth.oauth.support.LoginTargetAuthenticationEntryPoint;
import pers.ken.rt.auth.oauth.support.RedisOAuth2AuthorizationService;
import pers.ken.rt.auth.oauth.support.password.PasswordAuthenticationConverter;
import pers.ken.rt.auth.oauth.support.password.PasswordAuthenticationProvider;
import pers.ken.rt.auth.oauth.support.password.PasswordAuthenticationToken;
import pers.ken.rt.auth.oauth.utils.AuthorizationSupporter;
import pers.ken.rt.auth.oauth.utils.Jwks;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <name> AuthorizationServerConfig </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:10.
 *
 * @author _Ken.Hu
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {
    private static final String LOGIN_URL = "/login";


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      OAuth2TokenGenerator<?> tokenGenerator,
                                                                      RegisteredClientRepository registeredClientRepository,
                                                                      UserDetailsService userDetailsService,
                                                                      PasswordEncoder passwordEncoder) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http
            .cors(Customizer.withDefaults())
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            // Enable OpenID Connect 1.0
            .oidc(Customizer.withDefaults())
            // 设置自定义用户确认授权页
            .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.errorResponseHandler(AuthorizationSupporter::exceptionHandler))
            .tokenEndpoint(tokenEndpoint ->
                // 支持密码认证(Oauth2.1已经废弃PASSWORD模式，自定义实现)
                tokenEndpoint
                    .accessTokenRequestConverter(new PasswordAuthenticationConverter())
                    .authenticationProvider(new PasswordAuthenticationProvider(authorizationService, tokenGenerator, passwordEncoder, registeredClientRepository, userDetailsService))
                    .errorResponseHandler(AuthorizationSupporter::exceptionHandler)
            )
        ;
        http.exceptionHandling(exceptions ->
                exceptions
                    // 前后端分离 不需要重定向了
                    .defaultAuthenticationEntryPointFor(
                        new LoginTargetAuthenticationEntryPoint(LOGIN_URL),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
                    .authenticationEntryPoint(AuthorizationSupporter::exceptionHandler)
                    .accessDeniedHandler(AuthorizationSupporter::exceptionHandler)
            )
        ;
        return http.build();
    }

    /**
     * 配置客户端Repository
     *
     * @param jdbcTemplate
     * @return
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("channel")
            .clientSecret(passwordEncoder.encode("GzVBhcAx2tYvQxmcjWhV"))
            // 客户端认证方式，基于请求头的认证
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            // 配置资源服务器使用该客户端获取授权时支持的方式
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.PASSWORD)
            .redirectUri("http://127.0.0.1:38081/login/oauth2/code/channel")
            .redirectUri("https://www.baidu.com")
            .redirectUri("http://127.0.0.1:5173/OAuth2Redirect")
            // 该客户端的授权范围，OPENID与PROFILE是IdToken的scope，获取授权时请求OPENID的scope时认证服务会返回IdToken
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            // 自定义scope
            .scope("read")
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
            .build();
        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        // 做了个初始化
        RegisteredClient messagingClient = jdbcRegisteredClientRepository.findByClientId(registeredClient.getClientId());
        if (Objects.isNull(messagingClient)) {
            jdbcRegisteredClientRepository.save(registeredClient);
        }
        // TODO 设备码授权客户端 Just a Test
        RegisteredClient deviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("device-message-client")
            // 公共客户端
            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
            // 设备码授权
            .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            // 自定scope
            .scope("message.read")
            .scope("message.write")
            .build();
        RegisteredClient byClientId = jdbcRegisteredClientRepository.findByClientId(deviceClient.getClientId());
        if (byClientId == null) {
            jdbcRegisteredClientRepository.save(deviceClient);
        }
        return jdbcRegisteredClientRepository;
    }


    /**
     * 配置jwk源，使用非对称加密，公开用于检索匹配指定选择器的JWK的方法
     *
     * @return
     */
    @SneakyThrows
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        // Jwks.generateRsa() 可以生成随机的 但是每次重启会导致之前的JWTtoken解析失败，因为KID采用随机生成
        String rsaKey = Jwks.getRsaStringFromClassPath("jwks.json");
        JWKSet jwkSet = JWKSet.parse(rsaKey);
        // 旧的写法 (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * 配置jwt解析器
     *
     * @param jwkSource
     * @return
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


    /**
     * 添加认证服务器配置，设置jwt签发者、默认端点请求地址等
     *
     * @return
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


    /**
     * 配置基于redis的oauth2的授权管理服务
     *
     * @return
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisOAuth2AuthorizationService(redisTemplate);
    }

    /**
     * 配置基于db的授权确认管理服务
     *
     * @param jdbcTemplate
     * @param registeredClientRepository
     * @return OAuth2AuthorizationConsentService
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }


    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * 自定义增强jwt，自定义相关信息存放到jwt_token里面
     *
     * @return OAuth2TokenCustomizer<JwtEncodingContext>
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        return context -> {
            JwsHeader.Builder headers = context.getJwsHeader();
            // token_type 设置到头部
            headers.header(SecurityConstant.Additional.TOKEN_TYPE, context.getTokenType().getValue());
            JwtClaimsSet.Builder claims = context.getClaims();
            if (context.getPrincipal() instanceof PasswordAuthenticationToken token) {
                // 补充account_id到jwtToken信息
                jwtClaimsSet(claims, token.getAuthUserDetails());
            }
            if (context.getPrincipal().getPrincipal() instanceof UserDetails user) {
                // 获取申请的scopes
                Set<String> scopes = context.getAuthorizedScopes();
                // 获取用户的权限
                Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                // 提取权限并转为字符串
                Set<String> authoritySet = Optional.ofNullable(authorities).orElse(Collections.emptyList()).stream()
                    // 获取权限字符串
                    .map(GrantedAuthority::getAuthority)
                    // 去重
                    .collect(Collectors.toSet());

                // 合并scope与用户信息
                authoritySet.addAll(scopes);

                // 将权限信息放入jwt的claims中（也可以生成一个以指定字符分割的字符串放入）
                claims.claim("authorities", authoritySet);
                if (user instanceof AuthUserDetails details) {
                    jwtClaimsSet(claims, details);
                }
            }
        };
    }


    private void jwtClaimsSet(JwtClaimsSet.Builder claims, AuthUserDetails authUserDetails) {
        if (null == authUserDetails) {
            return;
        }
        claims.claim(SecurityConstant.Additional.ACCOUNT_ID, String.valueOf(authUserDetails.getUserId()));
        claims.claim(SecurityConstant.Additional.TENANT_CODE, authUserDetails.getTenantCode());
        claims.claim(SecurityConstant.Additional.TENANT_ID, String.valueOf(authUserDetails.getTenantId()));
        claims.claim(SecurityConstant.Additional.NAME, String.valueOf(authUserDetails.getName()));
        claims.claim(SecurityConstant.Additional.STATUS, String.valueOf(authUserDetails.getStatus()));
        claims.claim(SecurityConstant.Additional.ROLES, authUserDetails.getRoles());
    }

    /**
     * 自定义jwt解析器，设置解析出来的权限信息的前缀与在jwt中的key
     *
     * @return JwtAuthenticationConverter jwt解析器
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置解析权限信息的前缀，设置为空是去掉前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        // 设置权限信息在jwt claims中的key
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JwtEncoder jwtEncoder, OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer) {
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(tokenCustomizer);
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
            jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

}
