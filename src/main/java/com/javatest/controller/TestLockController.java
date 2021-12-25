package com.javatest.controller;

import java.util.concurrent.CountDownLatch;

import com.javatest.config.DistributedRedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lock")
public class TestLockController {

    @Autowired
    private DistributedRedisLock distributedRedisLock;

    // redision分布式锁id
    private static String lock = "lock_id";

    // 测试数据key
    private static String dataKey = "lock_count";

    // 测试数据value
    private int count = 100;

    // 模拟线程数
    private int threadNumber = 100;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * @title 在redis中初始化设置lock_count=100，用于测试
     * @author gavin
     * @date 2020年8月26日
     */
    @GetMapping("lockInit")
    public Object testInit() {
        this.stringRedisTemplate.opsForValue().set(dataKey, String.valueOf(this.count));
        return this.stringRedisTemplate.opsForValue().get(dataKey);
    }

    /**
     * @title 测试不加锁
     * @author gavin
     * @date 2020年8月26日
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/testNoLock")
    public Object testNoLock() throws InterruptedException {
        // 多线程计数器
        CountDownLatch latch = new CountDownLatch(this.threadNumber);
        for(int i=0;i<this.threadNumber;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String valueStr = stringRedisTemplate.opsForValue().get(dataKey);
                    int value = Integer.valueOf(valueStr);
                    value = value - 1;
                    stringRedisTemplate.opsForValue().set(dataKey, String.valueOf(value));
                    // 每个线程执行完毕，计数器减1
                    latch.countDown();
                }
            }).start();
        }
        // 在此阻塞，等待所有线程结束
        latch.await();
        System.out.println(this.threadNumber + "个线程全部执行完毕");
        // 返回最终的dataKey
        return stringRedisTemplate.opsForValue().get(dataKey);
    }

    /**
     * @title 测试分布式锁
     * @author gavin
     * @date 2020年8月26日
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/testUseLock")
    public Object testLock() throws InterruptedException {
        // 多线程计数器
        CountDownLatch latch = new CountDownLatch(this.threadNumber);
        for(int i=0;i<this.threadNumber;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean lockFlag = distributedRedisLock.lock(lock);
                    // 加分布式锁
                    if(lockFlag) {
                        String valueStr = stringRedisTemplate.opsForValue().get(dataKey);
                        int value = Integer.valueOf(valueStr);
                        value = value - 1;
                        stringRedisTemplate.opsForValue().set(dataKey, String.valueOf(value));
                        // 加锁
                        distributedRedisLock.unlock(lock);
                        // 每个线程执行完毕，计数器减1
                        latch.countDown();
                    }
                }
            }).start();
        }
        // 在此阻塞，等待所有线程结束
        latch.await();
        System.out.println(this.threadNumber + "个线程全部执行完毕");
        // 返回最终的dataKey
        return stringRedisTemplate.opsForValue().get(dataKey);
    }
}


