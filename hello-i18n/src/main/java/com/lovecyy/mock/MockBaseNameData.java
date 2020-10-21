package com.lovecyy.mock;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 国际化basename分组
 */
public class MockBaseNameData {
    /**
     * 存放主键id 对应baseName值
     */
    private static final Map<Integer,String> baseNames=new HashMap<>();

    static {
        baseNames.put(1,"i18n/messages");
        baseNames.put(2,"i18n/users");
        baseNames.put(3,"i18n/shops");
    }

    /**
     * 获取所有的基础文件名
     * @return
     */
    public static Set<String> getAllBaseNames(){
        Collection<String> values = baseNames.values();
        return values.stream().collect(Collectors.toSet());
    }

    /**
     * 根据id获取基础名称
     * @param id
     * @return
     */
    public static String getById(Integer id){
        return baseNames.get(id);
    }

    /**
     * 根据id删除
     * @param id
     */
    public static void removeById(Integer id){
        baseNames.remove(id);
    }
}
