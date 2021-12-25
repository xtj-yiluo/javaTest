package com.javatest.config;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @title 分布式redis锁
 * @author gavin
 * @date 2020年7月10日
 */
@Component
public class DistributedRedisLock {
    @Autowired
    private RedissonClient redissonClient;

    private Logger logger = LoggerFactory.getLogger(getClass());;

    // 加锁
    public Boolean lock(String lockName) {
        try {
            if (redissonClient == null) {
                logger.info("redissonClient为空");
                return false;
            }
            RLock lock = redissonClient.getLock(lockName);
            // 锁30秒后自动释放，防止死锁
            lock.lock(30, TimeUnit.SECONDS);

            logger.info("线程" + Thread.currentThread().getName() + "加锁" + lockName + "成功");
            // 加锁成功
            return true;
        } catch (Exception e) {
            logger.error("加锁异常:" + lockName);
            e.printStackTrace();
            return false;
        }
    }

    // 释放锁
    public Boolean unlock(String lockName) {
        try {
            if (redissonClient == null) {
                logger.info("redissonClient为空");
                return false;
            }
            RLock lock = redissonClient.getLock(lockName);

            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    // 主动释放锁
                    lock.unlock();
                    logger.info("线程" + Thread.currentThread().getName() + "解锁" + lockName + "成功");
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(Thread.currentThread().getName() + "解锁异常:" + lockName);
            e.printStackTrace();
            return false;
        }
    }
}


