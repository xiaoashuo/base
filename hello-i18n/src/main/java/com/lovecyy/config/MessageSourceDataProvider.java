package com.lovecyy.config;

import java.util.Locale;
import java.util.Set;

/**
 * 提供消息源数据
 * @author Yakir
 */
public interface MessageSourceDataProvider {
    /**
     * 加载所有基础名
     * @return
     */
    default Set<String> loadBaseNames(){
        return null;
    }
    /**
     * 加载所有keys通过基础名和区域
     * @param baseName
     * @param locale
     * @return
     */
    default Set<String> loadKeys(String baseName, Locale locale){
        return null;
    }

    /**
     * 加载对应基础名和区域的key对应的值
     * @param baseName
     * @param locale
     * @param key
     * @return
     */
    default String loadVal(String baseName,Locale locale,String key){
        return null;
    }

}
