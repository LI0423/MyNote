package com.video.user.util.concurrent;

public interface DistributedLockFactory {
    DistributedLock create(String lockKey);
}
