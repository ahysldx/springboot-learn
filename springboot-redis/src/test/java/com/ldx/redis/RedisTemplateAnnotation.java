package com.ldx.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class RedisTemplateAnnotation {
    @Resource
    private UserService userService;

    @Test
    public void test() {
        System.out.println(userService.insertUser());
        System.out.println(userService.getUser());
        System.out.println(userService.getUser());
        System.out.println("更新缓存");
        System.out.println(userService.updateUser());
        System.out.println(userService.getUser());
        System.out.println("删除缓存");
        System.out.println(userService.deleteUser());
        System.out.println(userService.getUser());
        System.out.println(userService.getUser());
    }


}
