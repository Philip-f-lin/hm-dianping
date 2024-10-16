package com.hmdp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    private RLock lock;

    @BeforeEach
    void setUp(){
        lock = redissonClient.getLock("order");
    }

    @Test
    void method1(){
        // 嘗試獲取鎖
        boolean isLock = lock.tryLock();
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
