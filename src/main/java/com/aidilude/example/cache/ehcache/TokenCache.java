package com.aidilude.example.cache.ehcache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = {"token"})
public class TokenCache {

    @CachePut(value = "token", key = "#customerId")
    public String putToken(Integer customerId, String token){
        return token;
    }

    @Cacheable(value = "token", key = "#customerId")
    public String getToken(Integer customerId){
        return "no exist";
    }

    //将验证码从缓存中删除，beforeInvocation = true：在方法执行前清除缓存
    @CacheEvict(value = "token", key = "#customerId", beforeInvocation = true)
    public boolean evictToken(Integer customerId){
        return true;
    }

}
