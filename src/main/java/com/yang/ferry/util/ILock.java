package com.yang.ferry.util;

public interface ILock {
    boolean tryLock(long timeoutSec);
    void unlock();
}
