package com.app;

import com.app.utils.redis.LockHelper;
import com.app.utils.redis.RedisUtils;
import com.baomidou.lock.LockInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedissionTest {

    @Autowired
    private LockHelper lockHelper;

    @Test
    public void test() {
        // 原子
        RedisUtils.setAtomicLong("count", 1L);
        RedisUtils.incr("count");
        long count = RedisUtils.getAtomicLong("count");
        System.out.println(count);
        // object
        RedisUtils.setCacheObject("name", "zs");
        String name = RedisUtils.getCacheObject("name");
        System.out.println(name);
        RedisUtils.deleteObject("name");
    }

    /**
     * 30s（锁释放时间）内执行lockTest和lockTest2，发现一个拿到锁另一个没有拿到锁
     */
    @Test
    public void lockTest() {
        LockInfo lockInfo = lockHelper.lock("lockKey:1");
        System.out.println(lockInfo);
    }

    @Test
    public void lockTest2() {
        LockInfo lockInfo = lockHelper.lock("lockKey:1");
        System.out.println(lockInfo);
    }
}
