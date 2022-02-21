package com.test.mina;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FireWallConfig {

    /**
     * 每秒最大收包数量
     */

    @Value("${mina.firewall.config.maxPackagePerSecond}")
    private int maxPackagePerSecond;

    /**
     * XX秒为洪水窗口期
     */
    @Value("${mina.firewall.config.floodWindowSeconds}")
    private int floodWindowSeconds;

    /**
     * 窗口期超过多少次即判定有效
     */
    @Value("${mina.firewall.config.maxFloodTimes}")
    private int maxFloodTimes;

    /**
     * 匿名链接存活毫秒
     */
    @Value("${mina.firewall.config.anonymousAliveMilli}")
    private int anonymousAliveMilli;

    private static volatile FireWallConfig self;

    public int getMaxPackagePerSecond() {
        return maxPackagePerSecond;
    }

    public void setMaxPackagePerSecond(int maxPackagePerSecond) {
        this.maxPackagePerSecond = maxPackagePerSecond;
    }


    public int getFloodWindowSeconds() {
        return floodWindowSeconds;
    }

    public void setFloodWindowSeconds(int floodWindowSeconds) {
        this.floodWindowSeconds = floodWindowSeconds;
    }

    public int getMaxFloodTimes() {
        return maxFloodTimes;
    }

    public void setMaxFloodTimes(int maxFloodTimes) {
        this.maxFloodTimes = maxFloodTimes;
    }


    public int getAnonymousAliveMilli() {
        return anonymousAliveMilli;
    }

    @Override
    public String toString() {
        return "FireWallConfig [maxPackagePerSecond=" + maxPackagePerSecond + ", floodWindowSeconds="
                + floodWindowSeconds + ", maxFloodTimes=" + maxFloodTimes + "]";
    }
}

