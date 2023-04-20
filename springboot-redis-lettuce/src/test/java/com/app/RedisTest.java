package com.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void redisTest() {
        redisTemplate.opsForValue().set("name", "zs");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);

        redisTemplate.delete("name");
        name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

}
