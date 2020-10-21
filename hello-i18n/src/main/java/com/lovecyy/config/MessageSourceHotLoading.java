package com.lovecyy.config;

public interface MessageSourceHotLoading {
    /**
     * 重新加载消息源basenames
     */
     void reloadBaseNames();

    /**
     * 清除本地缓存
     */
    void clearLocalCache();
}
