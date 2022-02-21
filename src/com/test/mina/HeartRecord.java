package com.test.mina;


import lombok.Getter;
import lombok.Setter;

/**
 * 心跳记录
 */
@Setter
@Getter
public class HeartRecord {
    /**
     * 上次心跳时间
     */
    private long lastTime;
    /**
     * 本次心跳时间
     */
    private long currTime;
    /**
     * 非法心跳次数
     */
    private int illegalCount;

    /**
     * 记录非法心跳次数 并返回是否达到上限
     */
    public boolean addCount() {
        illegalCount++;
        return illegalCount >= 15;
    }
}
