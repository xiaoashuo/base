package com.lovecyy.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MockDataSource {

    public static final ConcurrentHashMap<String,MessageInfo> messageData=new ConcurrentHashMap<>();
    static {
        MessageInfo data1 = new MessageInfo("i18n/messages", "en_US", "sql.test", "sql test");
        MessageInfo data2 = new MessageInfo("i18n/messages", "zh_CN", "sql.test", "sql测试");
        messageData.put("i18n/messages_en_US",data1);
        messageData.put("i18n/messages_zh_CN",data2);
    }

    @Data
    @AllArgsConstructor
    static
    class MessageInfo{
        private String baseName;
        private String locale;
        private String k;
        private String v;
    }

    public static void addData(){
        MessageInfo data1 = new MessageInfo("i18n/messages", "en_US", "sql.name", "sql name");
        MessageInfo data2 = new MessageInfo("i18n/messages", "zh_CN", "sql.name", "sql名称");
        messageData.put("i18n/shop/shop_en_US",data1);
        messageData.put("i18n/shop/_zh_CN",data2);

    }

    public static void removeData(){
        messageData.remove("i18n/messages_en_US");
        messageData.remove("i18n/messages_zh_CN");
    }

    /**
     * 加载数据从缓存 或数据库
     * @param baseName
     * @param locale
     * @param key
     * @return
     */
    public static String loadMessageFromCache(String baseName, Locale locale,String key){
        Collection<MessageInfo> values = messageData.values();
        for (MessageInfo value : values) {
            if (value.baseName.equals(baseName)&&locale.toString().equals(value.getLocale())&&
            key.equals(value.getK())){
                return value.getV();
            }
        }
        return null;
    }
    public static Set<String> loadKeysFromCache(String baseName, Locale locale){
        Set<String> keys=new HashSet<>();
        Collection<MessageInfo> values = messageData.values();
        String localeText=locale.toString();
        for (MessageInfo value : values) {
            if (value.locale.equals(localeText)){
                keys.add(value.k);
            }
        }
        return keys;
    }
}
