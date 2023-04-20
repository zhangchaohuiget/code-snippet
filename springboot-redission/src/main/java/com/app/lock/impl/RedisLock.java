package com.app.lock.impl;

import com.app.lock.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 自己实现的redis分布式锁实现
 */
@Component
public class RedisLock implements Lock {
    private final static Logger log = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     *
     * @param key   锁key
     * @param value 当前时间+超时时间
     * @return 加索结果
     */
    @Override
    public boolean lock(String key, String value) {
        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
        if (setIfAbsent != null && setIfAbsent) {
            return true;
        }
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            return !StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue);
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key 锁key
     */
    @Override
    public void unlock(String key) {
        stringRedisTemplate.opsForValue().getOperations().delete(key);
    }

    /**
     * 解锁
     *
     * @param key   锁key
     * @param value 锁值
     */
    @Override
    public void unlock(String key, String value) {
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
            stringRedisTemplate.opsForValue().getOperations().delete(key);
        }
    }
}
