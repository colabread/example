package com.aidilude.example.cache.ehcache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = {"sm"})
public class SMCache {

    @CachePut(value = "sm", key = "#phone")
    public String putSMCode(String phone, String code){
        return code;
    }

    @Cacheable(value = "sm", key = "#phone")
    public String getSMCode(String phone){
        return "no exist";
    }

    //将验证码从缓存中删除，beforeInvocation = true：在方法执行前清除缓存
    @CacheEvict(value = "sm", key = "#phone", beforeInvocation = true)
    public boolean evictSMCode(String phone){
        return true;
    }

}
