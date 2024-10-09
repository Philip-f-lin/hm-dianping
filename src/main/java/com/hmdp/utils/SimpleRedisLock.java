package com.hmdp.utils;

import cn.hutool.core.lang.UUID;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SimpleRedisLock implements ILock {

    private String name;
    private StringRedisTemplate stringRedisTemplate;

    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String KEY_PREFIX = "lock:";
    private static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    @Override
    public boolean tryLock(long timeoutSec) {
        // 取得線程標示
        long threadId = Thread.currentThread().getId();
        // 取得鎖
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(KEY_PREFIX + name, threadId + "", timeoutSec, TimeUnit.SECONDS);
        // Boolean 是包裝類，返回值可能為 null，直接比較可能導致空指針異常
        // 因此使用 Boolean.TRUE.equals(success) 來避免自動拆箱問題
        return Boolean.TRUE.equals(success);
    }

    /*@Override
    public void unlock() {
        // 呼叫lua腳本
        stringRedisTemplate.execute(
                UNLOCK_SCRIPT,
                Collections.singletonList(KEY_PREFIX + name),
                ID_PREFIX + Thread.currentThread().getId());
    }*/
    @Override
    public void unlock() {
        // 釋放鎖
        stringRedisTemplate.delete(KEY_PREFIX + name);
        /*// 取得線程標示
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        // 取得鎖中的標示
        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        // 判斷標示是否一致
        if(threadId.equals(id)) {
            stringRedisTemplate.delete(KEY_PREFIX + name);
        }*/
    }
}
