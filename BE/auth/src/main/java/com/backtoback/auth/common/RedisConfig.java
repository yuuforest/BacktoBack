package com.backtoback.auth.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
/*
    application.yml로부터
    redis host, port를 가져온다.
*/
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

<<<<<<< HEAD
    // @Value("${spring.data.redis.password}")
    // private String redisPassword;
=======
    @Value("${spring.data.redis.password}")
    private String redisPassword;
>>>>>>> d3e242c9852359e36e08f18e14d7f969c3bdb292

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
<<<<<<< HEAD
        // redisStandaloneConfiguration.setPassword(redisPassword);
=======
        redisStandaloneConfiguration.setPassword(redisPassword);
>>>>>>> d3e242c9852359e36e08f18e14d7f969c3bdb292
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());

        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());

        return stringRedisTemplate;
    }
}
