package pers.ken.rt.uaa.oauth;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import pers.ken.rt.uaa.oauth.model.OauthUserDetail;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Map;

/**
 * <name> AuthorizationServerConfig </name>
 * <desc> </desc>
 * Creation Time: 2021/9/20 22:10.
 *
 * @author _Ken.Hu
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final ClientDetailsService clientDetailsService;
    private final UserDetailsService userDetailsService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator;


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * ???????????? ??????????????????Jwt??????
     *
     * @return {@link TokenEnhancer}
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = Maps.newHashMap();
            OauthUserDetail oauthUser = (OauthUserDetail) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put("userId", oauthUser.getId());
            additionalInfo.put("username", oauthUser.getUsername());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("ken-jwt.jks"),
                "123456".toCharArray());
        return factory.getKeyPair("ken-jwt", "123456".toCharArray());
    }


    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder())
                //??????token???????????????????????????
                .tokenKeyAccess("permitAll()")
                //??????token??????????????????????????????
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                // ?????????
                .authenticationEntryPoint(authenticationEntryPoint)
                // ?????????
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetailsService);
        clients.inMemory()
                .withClient("usercenter")
                .scopes("all")
                .secret(passwordEncoder().encode("123456"))
                .resourceIds("usercenter", "gateway", "order")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .and()
                .withClient("gateway")
                .scopes("all")
                .secret(passwordEncoder().encode("123456"))
                .resourceIds("usercenter", "gateway", "order")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .and()
                .withClient("order")
                .scopes("all")
                .secret(passwordEncoder().encode("123456"))
                .resourceIds("usercenter", "gateway", "order")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .userDetailsService(userDetailsService)
                .exceptionTranslator(webResponseExceptionTranslator)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //refresh token????????????????????????????????????(true)??????????????????(false)????????????true
                //???????????????access token?????????????????? refresh token?????????????????????????????????????????????????????????
                //??????????????????access token?????????????????? refresh token????????????????????????refresh token??????????????????????????????????????????????????????????????????
                .reuseRefreshTokens(true);
    }
}
