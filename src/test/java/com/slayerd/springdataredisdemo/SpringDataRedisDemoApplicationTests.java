package com.slayerd.springdataredisdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slayerd.springdataredisdemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;

@SpringBootTest
class SpringDataRedisDemoApplicationTests {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //SpringMVC自带的Json序列化工具
    private static final ObjectMapper mapper = new ObjectMapper();
    @Test
    void contextLoads() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","slayerd#spring-data-redis-demo");
        System.out.println(valueOperations.get("name"));
    }

    @Test
    void contextLoads1() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user:1",new User(1,"slayerd"));
        Object o = valueOperations.get("user:1");
        System.out.println(valueOperations.get("user:1"));
        System.out.println(valueOperations.get("user:1").getClass());
    }

    @Test
    void contextLoads2() throws JsonProcessingException {
        User user = new User(1, "huge");
        String userAsString = mapper.writeValueAsString(user);
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("name",userAsString);
        String name = stringStringValueOperations.get("name");
        System.out.println(mapper.readValue(name,User.class));
    }

    @Test
    void testHash() throws JsonProcessingException {
        stringRedisTemplate.opsForHash().put("user:100","name","huge");
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:100");
        System.out.println(entries);
    }

}
