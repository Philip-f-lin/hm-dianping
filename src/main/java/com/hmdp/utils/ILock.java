package com.hmdp.utils;

public interface ILock {

    /**
     * 嘗試取得鎖
     * @param timeoutSec 鎖持有的超時時間，過期後自動釋放
     * @return true代表取得鎖定成功; false代表取得鎖定失敗
     */
    boolean tryLock(long timeoutSec);

    /**
     * 釋放鎖
     */
    void unlock();
}
