package com.lovecyy.apache.shardingsphere.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lovecyy.apache.shardingsphere.mapper.TbOrderMapper;
import com.lovecyy.apache.shardingsphere.service.api.TbOrderService;
/**
* @topic 
*
* @author ys
* @date 2020/4/11 0:30
*/
@Service
public class TbOrderServiceImpl implements TbOrderService{

    @Resource
    private TbOrderMapper tbOrderMapper;

}
