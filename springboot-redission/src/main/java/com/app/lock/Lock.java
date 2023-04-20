package com.app.lock;

/**
 * 自己实现的redis分布式锁redis分布式锁
 */
public interface Lock {
    /**
     * 加锁
     *
     * @param key   锁key
     * @param value 当前时间+超时时间
     * @return 加索结果
     */
    boolean lock(String key, String value);

    /**
     * 解锁
     *
     * @param key 锁key
     */
    void unlock(String key);

    /**
     * 解锁
     *
     * @param key   锁key
     * @param value 锁值
     */
    void unlock(String key, String value);
}
