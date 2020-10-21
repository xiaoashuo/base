package com.lovecyy.mock;

public interface LocaleCache {
    /**
     * 删除对应的code项
     * @param baseName
     * @param code
     */
    default void removeItem(String baseName,String code){
        return;
    }
}
