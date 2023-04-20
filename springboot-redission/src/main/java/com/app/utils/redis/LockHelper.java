package com.app.utils.redis;

import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import com.baomidou.lock.spring.boot.autoconfigure.Lock4jProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis分布式锁（baomidou实现）
 */
@Component
public class LockHelper {

    @Autowired
    private LockTemplate lockTemplate;
    @Autowired
    private Lock4jProperties properties;

    /**
     * 加锁
     *
     * @param key
     * @return
     */
    public LockInfo lock(String key) {
        LockInfo lockInfo = lockTemplate.lock(key, properties.getExpire(), properties.getAcquireTimeout(), RedissonLockExecutor.class);
        return lockInfo;
    }

    /**
     * 释放锁
     *
     * @param lockInfo
     * @return
     */
    public Boolean unLock(LockInfo lockInfo) {
        return lockTemplate.releaseLock(lockInfo);
    }
}
