package com.video.oversea.user.util.concurrent;

/**
 * 分布式锁接口
 * TODO: 先简单实现一版，后续需要做wait notify时继承java.util.concurrent.locks.Lock接口，实现Condition
 */
public interface DistributedLock {

    void lock() throws Exception;

    /**
     * timeout <= 0则无限期等待
     * @param timeout
     * @throws Exception
     */
    void lock(long timeout) throws Exception;

    void unlock() throws Exception;
}
