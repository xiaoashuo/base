package com.lovecyy.high.concurrency.example2;

import org.apache.ibatis.annotations.*;

/**
 * @author ys
 * @topic
 * @date 2020/3/21 15:45
 */
@Mapper
public interface SeckillMapper {

    @Select("SELECT * FROM `seckill_test` WHERE goods_name=#{goodsName} ")
    @Results(id="Seckill", value={
            @Result(property="id",   column="id"),
            @Result(property="goodsName",  column="goods_name"),
            @Result(property="goodsNum", column="goods_num"),
            @Result(property="version", column="version"),
    })
    public Seckill selectByGoodsName(String goodsName);

  /*  @Select("SELECT * FROM `seckill_test` WHERE goods_name=#{goodsName} FOR UPDATE ")
    @Results(id="Seckill", value={
            @Result(property="id",   column="id"),
            @Result(property="goodsName",  column="goods_name"),
            @Result(property="goodsNum", column="goods_num"),
            @Result(property="version", column="version"),
    })*/
    public Seckill selectByGoodsNamePessimistic(String goodsName);

    @Update(" update `seckill_test` set `goods_num`=#{goodsNum} where `goods_name`=#{goodsName}")
    public int updateGoods(Seckill seckill);

    @Update(" update `seckill_test` set `goods_num`=#{goodsNum},`version`=`version`+1 where `goods_name`=#{goodsName} and `version`=#{version} ")
    public int updateGoodsVersion(Seckill seckill);
}
