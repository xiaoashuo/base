package com.lovecyy.high.concurrency.example2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ys
 * @topic
 * @date 2020/3/21 14:47
 */
@Service
public class SeckillService {



    @Autowired
    private SeckillMapper seckillMapper;



    public Seckill getByName(String goodsName){
        return seckillMapper.selectByGoodsName(goodsName);
    }
    /**
     * 不做任何处理的秒杀操作
     * 500人抢购100件商品 成功数才20左右 很多都是执行失败的
     * @param seckill
     * @return
     */
    public int seckillGoods(Seckill seckill){
        int countEffect=0;//影响行数
        Seckill daoSeckill = seckillMapper.selectByGoodsName(seckill.getGoodsName());
        if (daoSeckill.getGoodsNum()>0){
            daoSeckill.setGoodsNum(daoSeckill.getGoodsNum()-1);
            countEffect=seckillMapper.updateGoods(daoSeckill);
        }
        if (countEffect==1){
            System.out.println("抢到iphoneX成功");
        }else{
            System.out.println("抢到iphoneX失败");
        }
        return countEffect;
    }

    /**
     * 使用乐观锁的机制 可以解决并发
     * @param seckill
     * @return
     */
    public int seckillGoodsVersion(Seckill seckill){
        int countEffect=0;//影响行数
        Seckill daoSeckill = seckillMapper.selectByGoodsName(seckill.getGoodsName());
        if (daoSeckill.getGoodsNum()>0){
            daoSeckill.setGoodsNum(daoSeckill.getGoodsNum()-1);
            countEffect=seckillMapper.updateGoodsVersion(daoSeckill);
        }
        if (countEffect==1){
            System.out.println("抢到iphoneX成功");
        }else{
            System.out.println("抢到iphoneX失败");
        }
        return countEffect;
    }


    /**
     * 使用悲观锁的形式 可以保证库存正确性 可是countEffect影响行数有点多了
     * @param seckill
     * @return
     */
    @Transactional
    public int seckillGoodsPessimisticLock(Seckill seckill){

        int countEffect=0;//影响行数
        Seckill daoSeckill = seckillMapper.selectByGoodsNamePessimistic(seckill.getGoodsName());
        if (daoSeckill.getGoodsNum()>0){
            daoSeckill.setGoodsNum(daoSeckill.getGoodsNum()-1);
            countEffect=seckillMapper.updateGoods(daoSeckill);
        }
        if (countEffect==1){
            System.out.println("抢到iphoneX成功");
        }else{
            System.out.println("抢到iphoneX失败");
        }

        return countEffect;
    }

    /**
     * 同步锁实现秒杀 各方面数据都正常
     * @param seckill
     * @return
     */
    public int seckillGoodsSynchronized(Seckill seckill){
        synchronized (this){
            int countEffect=0;//影响行数
            Seckill daoSeckill = seckillMapper.selectByGoodsName(seckill.getGoodsName());
            if (daoSeckill.getGoodsNum()>0){
                daoSeckill.setGoodsNum(daoSeckill.getGoodsNum()-1);
                countEffect=seckillMapper.updateGoods(daoSeckill);
            }
            if (countEffect==1){
                System.out.println("抢到iphoneX成功");
            }else{
                System.out.println("抢到iphoneX失败");
            }

            return countEffect;
        }
    }
    private Lock lock=new ReentrantLock();

    /**
     * 可重入锁实现 可以保证库存正确性 以及输出打印
     * @param seckill
     * @return
     */
    public int seckillGoodsReentrantLock(Seckill seckill){
        int countEffect=0;//影响行数
        try {
            lock.lock();
            Seckill daoSeckill = seckillMapper.selectByGoodsName(seckill.getGoodsName());
            if (daoSeckill.getGoodsNum()>0){
                daoSeckill.setGoodsNum(daoSeckill.getGoodsNum()-1);
                countEffect=seckillMapper.updateGoods(daoSeckill);
            }
            if (countEffect==1){
                System.out.println("抢到iphoneX成功");
            }else{
                System.out.println("抢到iphoneX失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return countEffect;
    }

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * redis实现
     * @param seckill
     * @return
     */
    public int seckillGoodsRedis(Seckill seckill){
      int countEffect=0;//影响行数
      double goodsSum = redisTemplate.opsForValue().increment("goodsSum", -1).doubleValue();

      if (goodsSum>=0){
          System.out.println("抢到iphoneX成功,还剩下:"+goodsSum);
      }else{
          System.out.println("抢到iphoneX失败");
      }

        return countEffect;
    }
}
