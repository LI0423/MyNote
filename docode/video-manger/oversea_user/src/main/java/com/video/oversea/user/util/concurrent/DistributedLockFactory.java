package com.video.oversea.user.util.concurrent;

public interface DistributedLockFactory {
    DistributedLock create(String lockKey);
}
