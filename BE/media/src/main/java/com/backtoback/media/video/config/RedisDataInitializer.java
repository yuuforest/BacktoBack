package com.backtoback.media.video.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisDataInitializer {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @PostConstruct
  public void deleteRedisData() {
    redisTemplate.getConnectionFactory().getConnection().flushAll();
  }
}