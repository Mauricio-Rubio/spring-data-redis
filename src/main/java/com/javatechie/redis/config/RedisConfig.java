package com.javatechie.redis.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
    
        // Usamos GenericJackson2JsonRedisSerializer para serializar objetos en JSON
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        
        // Agregamos un tiempo de expiración de 60 segundos para cada objeto almacenado en Redis
        redisTemplate.expire("Users", Duration.ofSeconds(60));
    
        return redisTemplate;
    }
    
}
