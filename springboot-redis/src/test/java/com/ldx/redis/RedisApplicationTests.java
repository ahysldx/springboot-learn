package com.ldx.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void contextLoads() {

    }

    @Test
    public void stringTest() {
//        string（字符串）
        redisTemplate.opsForValue().set("A", new Date().toString());
        System.out.println("A的值是====>" + redisTemplate.opsForValue().get("A"));
    }

    @Test
    public void hashTest() {
//        hash（哈希）
        String key = "A_A";
        redisTemplate.delete(key);
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        HashMap<String, User> map = new HashMap<>();
        map.put("A", new User(new Date().toString()));
        map.put("B", new User(new Date().toString()));
        //全部添加
        ops.putAll(key, map);
        //添加单个
        ops.put(key, "C", new User(new Date().toString()));
        System.out.println("A_A的值是====>" + ops.entries(key));
        //为null时替换
        ops.putIfAbsent(key, "C", new User(new Date().toString() + "putIfAbsent"));
        System.out.println("C putIfAbsent A_A的值是====>" + ops.entries(key));
        ops.putIfAbsent(key, "D", new User(new Date().toString()));
        ops.putIfAbsent(key, "Z", System.currentTimeMillis());
        System.out.println("D putIfAbsent A_A的值是====>" + ops.entries(key));
        //删除key
        ops.delete(key, "C", "D");
        System.out.println("delete A_A的值是====>" + ops.entries(key));
        System.out.println("A的值是====>" + ops.get(key, "A"));
        System.out.println("X的值是====>" + ops.get(key, "X"));
        System.out.println("A_A的值是====>" + ops.entries(key));
        //增加1000
        ops.increment(key, "Z", 1000L);
        System.out.println("Z的值是====>" + ops.get(key, "Z"));
        redisTemplate.delete(key);

    }

    @Test
    public void listTest() {
//        list（列表）
        String key = "LIST-" + UUID.randomUUID().toString();
        ListOperations<String, String> ops = redisTemplate.opsForList();
        RedisOperations operations = ops.getOperations();
        System.out.println(operations);
        List<String> collect = Stream.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()).collect(Collectors.toList());
        ops.leftPush(key, UUID.randomUUID().toString());
        System.out.println(ops.leftPushAll(key, collect));
        System.out.println(ops.leftPop(key));
        System.out.println(ops.leftPop(key));
        redisTemplate.delete(key);
    }
    @Test
    public void setTest() {
//        set（集合）
        Set<String> set1 = new HashSet<>();
        set1.add("a1");
        Set<String> set = new HashSet<>();
        set.add("a1");
        set.add("a2");
        set.add("a3");
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add("A_B", "A", "B");
        setOperations.add("A_C", "A", "C");
        System.out.println(setOperations.members("A_B"));
        System.out.println(setOperations.difference("A_B", "A_C"));
        System.out.println(setOperations.difference("A_B", set));
    }

    @Test
    public void zsetTest() {
//        zset(sorted set：有序集合)
        redisTemplate.delete("fan1");
        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        //将值添加到键中的排序集合，如果已存在，则更新其分数。
        System.out.println(opsForZSet.add("fan1", "a", 1));//true （这里的1.0可以用1代替,因为用double收参）
        ZSetOperations.TypedTuple<String> objectTypedTuple1 = new DefaultTypedTuple<String>("b",2.0);//这里必须是2.0，因为那边是用Double收参
        ZSetOperations.TypedTuple<String> objectTypedTuple2 = new DefaultTypedTuple<String>("c",3.0);
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        System.out.println(opsForZSet.add("fan1",tuples));//2
        //通过索引区间返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        System.out.println(opsForZSet.range("fan1",0,-1));//[a, b, c]
        System.out.println(opsForZSet.range("fan1",-1,
                0));//[a, b, c]
        System.out.println(opsForZSet.count("fan1",1,1));
    }
}
