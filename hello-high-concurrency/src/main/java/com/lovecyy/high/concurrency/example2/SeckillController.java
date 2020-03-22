package com.lovecyy.high.concurrency.example2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ys
 * @topic
 * @date 2020/3/21 14:49
 */
@RestController
@RequestMapping("/seckill/")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("test")
    public ResponseEntity seckill(Seckill seckill){

        for(int i=0;i<1000;i++){
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    //不做任何处理的秒杀实现
                   seckillService.seckillGoods(seckill);
                    //乐观锁
                   // seckillService.seckillGoodsVersion(seckill);
                    //使用悲观锁机制
                 //  seckillService.seckillGoodsPessimisticLock(seckill);
                   //使用同步锁机制
                  // seckillService.seckillGoodsSynchronized(seckill);
                   //使用可重入锁
                   //seckillService.seckillGoodsReentrantLock(seckill);
                }
            });
            thread.start();
        }
       return ResponseEntity.ok("操作成功");
    }
    @PostMapping("redis/test")
    public ResponseEntity seckillRedis(Seckill seckill){
        Seckill seckill1 = seckillService.getByName(seckill.getGoodsName());
        redisTemplate.opsForValue().set("goodsSum",seckill1.getGoodsNum());
        for(int i=0;i<500;i++){
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    //不做任何处理的秒杀实现
                    // seckillService.seckillGoods(seckill);
                    //使用乐观锁机制
                    seckillService.seckillGoodsRedis(seckill);
                }
            });
            thread.start();
        }
        return ResponseEntity.ok("操作成功");
    }
}
