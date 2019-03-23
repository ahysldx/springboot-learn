package com.ldx.redis;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @CachePut(value = "user")
    public User insertUser() {
        return new User();
    }

    @Cacheable(value = "user")
    public User getUser() {
        return new User();
    }

    @CachePut(value = "user")
    public User updateUser() {
        return new User();
    }

    @CacheEvict(value = "user")
    public User deleteUser() {
        return new User();
    }
}
