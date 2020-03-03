package com.lovecyy.scheduling.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author ys
 * @topic
 * @date 2020/3/3 13:24
 */

public final class ScheduledTask {
    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
