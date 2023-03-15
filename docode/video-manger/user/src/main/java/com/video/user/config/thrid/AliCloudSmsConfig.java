package com.video.user.config.thrid;

import com.video.user.config.prop.AliCloudSmsWorkerProperties;
import com.video.user.config.prop.CacheRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class AliCloudSmsConfig {

    @Autowired
    AliCloudSmsWorkerProperties aliCloudSmsWorkerProperties;

    @Autowired
    CacheRedisProperties cacheRedisProperties;

    /**
     * 创建AliCloudSms工作线程池
     * @return 工作线程池
     */
    @Bean("aliCloudSmsWorkerThreadPool")
    public ThreadPoolExecutor workerThreadPool() {
        return new ThreadPoolExecutor(aliCloudSmsWorkerProperties.getMaxCount(),
                aliCloudSmsWorkerProperties.getMaxCount(),
                0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(aliCloudSmsWorkerProperties.getWorkQueueSize()),
                new ThreadFactory() {

                    private final AtomicInteger threadId = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("AliCloudSmsWorker-" + threadId.getAndIncrement());
                        return thread;
                    }
                });
    }

    /**
     * 用于订阅redis事管道的redis客户端(这个客户端会和redis保持长连接)
     * @return redis客户端
     */
    @Bean("aliCloudSmsListenerRedisClient")
    public Jedis redisClient() {
        return new Jedis(cacheRedisProperties.getHost(), cacheRedisProperties.getPort());
    }
}
