package com.aidilude.example.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.redis.");
    }

    //redis连接池配置
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.valueOf(propertyResolver.getProperty("max-active")));
        jedisPoolConfig.setMaxWaitMillis(Integer.valueOf(propertyResolver.getProperty("max-wait")));
        jedisPoolConfig.setMaxIdle(Integer.valueOf(propertyResolver.getProperty("max-idle")));
        jedisPoolConfig.setMinIdle(Integer.valueOf(propertyResolver.getProperty("min-idle")));
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnCreate(false);
        return jedisPoolConfig;
    }

    //redis连接工厂配置
    @Bean(name = "jedisConnectionFactory")
    public RedisConnectionFactory jedisConnectionFactory(@Qualifier(value = "jedisPoolConfig") JedisPoolConfig poolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setDatabase(Integer.valueOf(propertyResolver.getProperty("database")));
        jedisConnectionFactory.setHostName(propertyResolver.getProperty("host"));
        jedisConnectionFactory.setPort(Integer.valueOf(propertyResolver.getProperty("port")));
        jedisConnectionFactory.setPassword(propertyResolver.getProperty("password"));
        jedisConnectionFactory.setTimeout(Integer.valueOf(propertyResolver.getProperty("timeout")));
        return jedisConnectionFactory;
    }

    //redis数据库操作模板
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(@Qualifier(value = "jedisConnectionFactory") RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }



}