package pers.ken.rt.auth.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import pers.ken.rt.auth.oauth.support.Oauth2AuthorizationModule;
import pers.ken.rt.auth.oauth.support.RedisOAuth2AuthorizationService;

import java.util.List;

/**
 * @ClassName: RedisTemplateConfig
 * @Created: 2024/12/10 11:15
 * @Author ken
 */

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, AutowireCapableBeanFactory beanFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ClassLoader classLoader = RedisOAuth2AuthorizationService.class.getClassLoader();
        // 加载security提供的Modules
        List<Module> modules = SecurityJackson2Modules.getModules(classLoader);
        // 设置Value的序列化器为JSON，并包含类型信息
        ObjectMapper objectMapper = new ObjectMapper();
        // 启用默认类型处理
        objectMapper.registerModules(modules);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.registerModule(new CoreJackson2Module());
        objectMapper.registerModule(new Oauth2AuthorizationModule());
        objectMapper.setHandlerInstantiator(new SpringHandlerInstantiator(beanFactory));


//        objectMapper.activateDefaultTyping(
//            objectMapper.getPolymorphicTypeValidator(),
//            ObjectMapper.DefaultTyping.NON_FINAL,
//            JsonTypeInfo.As.PROPERTY);

        // 设置Key的序列化器为String
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);
        return template;
    }

}
