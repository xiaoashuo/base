package com.lovecyy.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 国际化数据模拟
 */
public class MockBaseLocaleData {
    /**
     * 区域项信息存储
     */
    private static final List<LocaleItem> localeItems=new ArrayList<>();
    static {
        //i18n/messages
        //加入sqltest 中英文
        localeItems.add(LocaleItem.builder().id(1).baseName("i18n/messages").locale("en_US").key("sql.test").value("sql.test").build());
        localeItems.add(LocaleItem.builder().id(2).baseName("i18n/messages").locale("zh_CN").key("sql.test").value("sql测试").build());
        localeItems.add(LocaleItem.builder().id(3).baseName("i18n/messages").locale("en_US").key("user").value("user").build());
        localeItems.add(LocaleItem.builder().id(4).baseName("i18n/messages").locale("zh_CN").key("user").value("用户").build());
        localeItems.add(LocaleItem.builder().id(5).baseName("i18n/messages").locale("en_US").key("user{0}").value("user{0}").build());
        localeItems.add(LocaleItem.builder().id(6).baseName("i18n/messages").locale("zh_CN").key("user{0}").value("用户{0}").build());
        //i18n/users
        localeItems.add(LocaleItem.builder().id(1).baseName("i18n/users").locale("en_US").key("user.sql.test").value("user.sql.test").build());
        localeItems.add(LocaleItem.builder().id(2).baseName("i18n/users").locale("zh_CN").key("user.sql.test").value("用户sql测试").build());
        //i18n/shops
        localeItems.add(LocaleItem.builder().id(1).baseName("i18n/shops").locale("en_US").key("shop.sql.test").value("shop.sql.test").build());
        localeItems.add(LocaleItem.builder().id(2).baseName("i18n/shops").locale("zh_CN").key("shop.sql.test").value("购物sql测试").build());

    }

    /**
     * 从缓存加载所有的keys
     * @param baseName
     * @param locale
     * @return
     */
     public static Set<String> loadKeysFromCache(String baseName, Locale locale){
         return localeItems.stream().filter(e -> baseName.equals(e.getBaseName()) && locale.toString().equals(e.getLocale())).map(e->e.getKey()).collect(Collectors.toSet());
     }

    /**
     * 从缓存中加载值
     * @param baseName
     * @param locale
     * @param key
     * @return
     */
     public static String loadValFromCache(String baseName,Locale locale,String key){
         return localeItems.stream().filter(e -> baseName.equals(e.getBaseName()) && locale.toString().equals(e.getLocale()) && key.equals(e.getKey()))
                 .map(e -> e.getValue()).findFirst().orElse(null);
     }

    public static void main(String[] args) {
        Set<String> en_us = loadKeysFromCache("i18n/messages", Locale.US);
        System.out.println(en_us);
        String s = loadValFromCache("i18n/messages", Locale.CHINA, "sql.test");
        System.out.println(s);
    }

}
