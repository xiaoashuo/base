package com.lovecyy.high.concurrency.example01;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author ys
 * @topic 队列+线程池的方式 实现高并发中的下单业务
 * @date 2020/3/21 10:43
 */
@Component
public class QueueThreadPool {

    //核心线程数量
    private static final int CORE_POOL_SIZE=2;
    //最大线程池数量
    private static final int MAX_POOL_SIZE=10;
    //线程池维护线程所允许的空闲时间
    private static final int KEEP_ALIVE_TIME=0;
    //线程池所使用的缓冲队列大小
    private static final int WORK_QUEUE_SIZE=50;
    /**
     * 用于存储在队列中的订单  防止重复提交 在真实场景中 可用redis代替 验证重复
     */
    Map<String,Object> cacheMap =new ConcurrentHashMap<>();
    /**
     * 订单的缓冲队列 当线程池满了 则将订单存入此缓冲队列
     */
    Queue<Object> msgQueue=new LinkedBlockingDeque<>();
    /**
     * 创建线程池
     */
    ThreadPoolExecutor threadPool=new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<>(WORK_QUEUE_SIZE), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
             msgQueue.offer(((BusinessThread)r).getAcceptStr());
            System.out.println("系统任务太忙了,把此订单交给调度线程池 注意处理 订单号"+((BusinessThread)r).getAcceptStr());
        }
    });

    /**
     * 将任务加入订单线程池
     * @param orderId
     */
    public void addOrders(String orderId){
        System.out.println("此订单准备添加线程池,订单号:"+orderId);
        //验证当前进入的订单是否已经存在
        if (cacheMap.get(orderId)==null){
            cacheMap.put(orderId,new Object());
        }
        BusinessThread businessThread = new BusinessThread(orderId);
        threadPool.execute(businessThread);
    }

    /**
     * 线程池的定时任务 称为调度线程池 此线程池支持定时以及周期性执行任务的需求
     */
    public ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(5);
    /**
     * 检查(调度线程池)，每秒执行一次，查看订单的缓冲队列是否有 订单记录，则重新加入到线程池
     */
    final ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            //判断缓冲队列是否存在记录
            if(!msgQueue.isEmpty()){
                //当线程池的队列容量少于WORK_QUEUE_SIZE，则开始把缓冲队列的订单 加入到 线程池
                if (threadPool.getQueue().size() < WORK_QUEUE_SIZE) {
                    String orderId = (String) msgQueue.poll();
                    BusinessThread businessThread = new BusinessThread(orderId);
                    threadPool.execute(businessThread);
                    System.out.println("(调度线程池)缓冲队列出现订单业务，重新添加到线程池，订单号："+orderId);
                }
            }
        }
    }, 0, 1, TimeUnit.SECONDS);

    /**终止订单线程池+调度线程池*/
    public void shutdown() {
        //true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止
        System.out.println("终止订单线程池+调度线程池："+scheduledFuture.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();

    }

    public Queue<Object> getMsgQueue() {
        return msgQueue;
    }
}
