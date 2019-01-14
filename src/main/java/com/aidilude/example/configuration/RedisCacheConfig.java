package com.aidilude.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 这里实现CachingConfigurerSupport主要是方便使用自定义keyGenerator
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport implements EnvironmentAware {

    @Autowired
    private RedisTemplate redisTemplate;

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.redis.");
    }

    /**
     * 配置redis缓存管理对象
     * @return
     */
    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        String[] cacheNames = {propertyResolver.getProperty("cache-name")};
        cacheManager.setCacheNames(Arrays.asList(cacheNames));
        //设置缓存过期时间(秒)
        cacheManager.setDefaultExpiration(Integer.valueOf(propertyResolver.getProperty("expiration-time")));
        return cacheManager;
    }


    /**
     * 生成key的策略
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样，key也会不一样。
     * @return
     */
    @Bean(name = "keyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append("_");
                sb.append(method.getName());
                sb.append("_");
                for (Object obj : params) {
                    sb.append(obj.toString());
                    sb.append("_");
                }
                return sb.toString();
            }
        };
    }
}