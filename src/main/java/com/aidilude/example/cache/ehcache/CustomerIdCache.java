package com.aidilude.example.cache.ehcache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = {"customerId"})
public class CustomerIdCache {

    @CachePut(value = "customerId", key = "#token")
    public Integer putCustomerId(String token, Integer customerId){
        return customerId;
    }

    @Cacheable(value = "customerId", key = "#token")
    public Integer getCustomerId(String token){
        return -1;
    }

    //将验证码从缓存中删除，beforeInvocation = true：在方法执行前清除缓存
    @CacheEvict(value = "customerId", key = "#token", beforeInvocation = true)
    public boolean evictCustomerId(String token){
        return true;
    }

}
