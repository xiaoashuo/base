package com.lovecyy.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;


/**
 * 消息源属性扩展
 * @author shuoyu
 */
@Data
public class MessageSourcePropertiesExtension extends MessageSourceProperties {
    /**
     * 基础扫描目录
     */
    private String baseFolder="i18n";
    /**
     * 是否包含子及子孙文件
     */
    private boolean includeChildFile=true;
    /**
     * 本地缓存
     */
    private boolean localCache=false;
    /**
     * 支持sql
     */
    private boolean supportSql=false;


}
