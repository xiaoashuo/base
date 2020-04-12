package com.lovecyy.apache.shardingsphere.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lovecyy.apache.shardingsphere.mapper.TbOrderItemMapper;
import com.lovecyy.apache.shardingsphere.service.api.TbOrderItemService;
/**
* @topic 
*
* @author ys
* @date 2020/4/11 0:30
*/
@Service
public class TbOrderItemServiceImpl implements TbOrderItemService{

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

}
