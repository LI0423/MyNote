package com.video.oversea.user.util.concurrent;

import com.video.oversea.user.util.IdentifierGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;

//TODO: 加看门狗
@Slf4j
public class RedisDistributedLockFactory implements DistributedLockFactory {

    private final ThreadLocal<String> identity;

    /**
     * 辅助分布式锁的redis pool
     */
    private final JedisPool jedisPool;

    private final Duration expire;

    public RedisDistributedLockFactory(JedisPool jedisPool, Duration expire) {
        this.jedisPool = jedisPool;
        this.expire = expire;
        this.identity = new ThreadLocal<>();
    }

    @Override
    public DistributedLock create(String lockKey) {
        return new RedisDistributedLock(lockKey);
    }

    private class RedisDistributedLock implements DistributedLock {

        /**
         * 分布式锁在redis中的标识用的key
         */
        private final String lockKey;

        RedisDistributedLock (String lockKey) {
            this.lockKey = lockKey;
        }

        @Override
        public void lock() throws Exception {
            lock(-1);
        }

        @Override
        public void lock(long timeout) throws Exception {
            try (Jedis jedis = jedisPool.getResource()) {

                // 设置线程id
                String myIdentityStr = String.valueOf(IdentifierGeneratorUtils.nextId());
                identity.set(myIdentityStr);

                log.info("RedisDistributedLock lock, lock key:[{}], my identity:[{}], timeout:[{}]",
                        lockKey, myIdentityStr, timeout);

                String script =
                        "local result = redis.call('setnx', KEYS[1], ARGV[1]) \n" +
                                "if (result == 1) then \n" +
                                "   redis.call('pexpire', KEYS[1], ARGV[2]) \n" +
                                "end \n" +
                                "return tostring(result) ";

                long nExpire = timeout <= 0 ? expire.toMillis() : timeout;
                Object res = jedis.eval(script, 1, this.lockKey, myIdentityStr, String.valueOf(nExpire));

                long nTimeout = timeout;
                long nStep = Math.min(nExpire/10, 5);

                while (!"1".equals(res.toString()) && (timeout <= 0 || nTimeout > 0)) {
                    // 当剩余的nTimeout小于nStep时,只能在sleep nTimeout的时间长度
                    nStep = nTimeout > 0 ? Math.min(nStep, nTimeout) : nStep;
                    Thread.sleep(nStep);
                    res = jedis.eval(script, 1, this.lockKey, myIdentityStr, String.valueOf(nExpire));
                    nTimeout -= nStep;
                }

                if (timeout > 0 && nTimeout <= 0) {
                    log.warn("RedisDistributedLock lock timeout, lock key:[{}], my identity:[{}], timeout:[{}], nTimeout[{}]",
                            lockKey, myIdentityStr, timeout, nTimeout);
                }
            }
        }

        @Override
        public void unlock() throws Exception {
            try (Jedis jedis = jedisPool.getResource()) {
                String identityStr = jedis.get(lockKey);
                String myIdentityStr = identity.get();
                if (!myIdentityStr.equals(identityStr)) {
                    throw new RuntimeException(String.format("unlock error, identity information does not match, " +
                            "result identity:[%s], my identity:[%s]", identityStr, myIdentityStr));
                }
                jedis.del(lockKey);
                log.info("RedisDistributedLock unlock, lock key:[{}], my identity:[{}]", lockKey, myIdentityStr);
            }
        }
    }
}
