package com.bigevent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
        //往redis中存储键值对
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("username", "zhangsan");
        ops.set("id", "1", 10, TimeUnit.SECONDS);
    }

    @Test
    public void testGet() {
        //从redis中获取键值对
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String id = ops.get("id");
        System.out.println(id);
    }

}
