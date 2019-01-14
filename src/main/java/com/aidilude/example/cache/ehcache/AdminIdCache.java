package com.aidilude.example.cache.ehcache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = {"adminId"})
public class AdminIdCache {

    @CachePut(value = "adminId", key = "#token")
    public Integer putAdminId(String token, Integer adminId){
        return adminId;
    }

    @Cacheable(value = "adminId", key = "#token")
    public Integer getAdminId(String token){
        return -1;
    }

    //将验证码从缓存中删除，beforeInvocation = true：在方法执行前清除缓存
    @CacheEvict(value = "adminId", key = "#token", beforeInvocation = true)
    public boolean evictAdminId(String token){
        return true;
    }

}
