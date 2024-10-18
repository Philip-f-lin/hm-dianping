package com.hmdp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedissonClient redissonClient2;

    @Resource
    private RedissonClient redissonClient3;

    private RLock lock;

    @BeforeEach
    void setUp(){
        RLock lock1 = redissonClient.getLock("order");
        RLock lock2 = redissonClient2.getLock("order");
        RLock lock3 = redissonClient3.getLock("order");

        // 創建連鎖 multiLock
        lock = redissonClient.getMultiLock(lock1, lock2, lock3);
    }

    @Test
    void method1() throws InterruptedException {
        // 嘗試獲取鎖
        boolean isLock = lock.tryLock(1L, TimeUnit.MINUTES);
        if (!isLock){
            log.error("獲取鎖失敗 ... 1");
            return;
        }
        try {
            log.info("獲取鎖成功 ... 1");
            method2();
            log.info("開始執行業務 ... 1");
        }finally {
            log.warn("準備釋放鎖");
            lock.unlock();
        }
    }

    void method2(){
        // 嘗試獲取鎖
        boolean isLock = lock.tryLock();
        if(!isLock){
            log.info("獲取鎖失敗 ... 2");
            return;
        }
        try {
            log.info("獲取鎖成功 ...2");
            log.info("開始執行業務 ... 2");
        }finally {
            log.warn("準備釋放鎖 ... 2");
            lock.unlock();
        }
    }
}
