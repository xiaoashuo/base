package com.lovecyy.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

/**
 * 消息源扩展
 * HierarchicalMessageSource接口添加了两个方法，建立父子层级的MessageSource结构，类似于前面我们所介绍的HierarchicalBeanFactory。
 *
 * 该接口的setParentMessageSource (MessageSource parent)方法用于设置父MessageSource，而getParentMessageSource()方法用于返回父MessageSource。
 * HierarchicalMessageSource接口最重要的两个实现类是ResourceBundleMessageSource和ReloadableResourceBundleMessageSource。它们基于Java的ResourceBundle基础类实现，允许仅通过资源名加载国际化资源。
 * ReloadableResourceBundleMessageSource提供了定时刷新功能，允许在不重启系统的情况下，更新资源的信息。
 * 在上面的配置中，我们通过cacheSeconds属性让ReloadableResourceBundleMessageSource每5秒钟刷新一次资源文件
 * （在真实的应用中，刷新周期不能太短，否则频繁的刷新将带来性能上的负面影响，一般不建议小于30分钟）。cacheSeconds默认值为-1表示永不刷新，此时，该实现类的功能就蜕化为ResourceBundleMessageSource的功能。
 * @author shuoyu
 */
@Slf4j
@RequiredArgsConstructor
public class MessageSourceExtension extends ResourceBundleMessageSource {
    private static final Resource[] NO_RESOURCES = {};

    private final MessageSourcePropertiesExtension messageSourceProperties;

    @PostConstruct
    public void init(){
        if (!StringUtils.isEmpty(messageSourceProperties.getBaseFolder())) {
            this.setBasenames(this.getBaseNames(messageSourceProperties.getBaseFolder(),messageSourceProperties.isIncludeChildFile()));
            fillMessageSource(this,messageSourceProperties);
        }
        //设置父MessageSource 子级找不到 会找父级
        ResourceBundleMessageSource parent = new ResourceBundleMessageSource();
        String basename = messageSourceProperties.getBasename();
        //是否是多个目录
        if (StringUtils.hasText(basename)) {
            parent.setBasenames(StringUtils.commaDelimitedListToStringArray(
                    StringUtils.trimAllWhitespace(basename)));
        }
        //设置文件编码
        fillMessageSource(parent,messageSourceProperties);
        this.setParentMessageSource(parent);

    }

    /**
     * 真正实现结果获取的地方
     * @param basename i18n/shop/shop
     * @param locale en_US
     * @return
     * @throws MissingResourceException
     */
    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        //父类MessageSource实现 读取的是配置文件的
        //ResourceBundle delegate = super.doGetBundle(basename, locale);
     try {

         ResourceBundle resourceBundle = new ResourceBundle() {
             @Override
             protected Object handleGetObject(String key) {
                 return   MockDataSource.loadMessageFromCache(basename, locale,key);
             }

             @Override
             public Enumeration<String> getKeys() {
                 log.info("获取基础名[{}]语言[{}]的key",basename,locale.toString());
                 Set<String> l = MockDataSource.loadKeysFromCache(basename, locale);
                 return Collections.enumeration(l);
             }
         };
         return resourceBundle;

     }catch (MissingResourceException ex){
         if (logger.isWarnEnabled()) {
             logger.warn("ResourceBundle [" + basename + "] not found for MessageSource: " + ex.getMessage());
         }
         // Assume bundle not found
         // -> do NOT throw the exception to allow for checking parent message source.
         return null;
     }

    }

    /**
     * 删除数据库参数这两个也要删除 虽然删除数据库已经看不到了 但会导致缓存冗余 在以下两个告诉内存
     *   ResourceBundleMessageSource
     *      cachedResourceBundles
     *      cachedBundleMessageFormats
     *  还有一种方式就是告诉缓存 我们自己配置 重写更高级的方法
     *
     *  getResourceBundle 无参
     *  getMessageFormat  有参会多带的
     */
    public void removeCachedResourceBundles(){

    }
    /**
     * 填充消息源
     * @param resourceBundleMessageSource
     * @param messageSourceProperties
     */
    private void fillMessageSource(ResourceBundleMessageSource resourceBundleMessageSource,MessageSourceProperties messageSourceProperties){
        //设置文件编码
        resourceBundleMessageSource.setDefaultEncoding(messageSourceProperties.getEncoding().name());
        resourceBundleMessageSource.setFallbackToSystemLocale(messageSourceProperties.isFallbackToSystemLocale());
        Duration cacheDuration = messageSourceProperties.getCacheDuration();
        if (cacheDuration != null) {
            resourceBundleMessageSource.setCacheMillis(cacheDuration.toMillis());
        }
        resourceBundleMessageSource.setAlwaysUseMessageFormat(messageSourceProperties.isAlwaysUseMessageFormat());
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(messageSourceProperties.isUseCodeAsDefaultMessage());
    }


    /**
     * 得到目录下所有文件名字
     * @param baseFolder
     * @return
     */
    private String[] getBaseNames(String baseFolder,boolean isIncludeChild){
        Set<String> baseNames=new HashSet<>();
        String realFolder=isIncludeChild?baseFolder+"/**/*":baseFolder;
        Resource[] resources = getResources( realFolder);
        for (Resource resource : resources) {
            if (resource.exists()) {
                try {
                    String urlPath = resource.getURI().toString();
                    String i18FileName = getI18FileName(urlPath.substring(urlPath.lastIndexOf(baseFolder)));
                    baseNames.add(i18FileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baseNames.toArray(new String[0]);
    }

    private static Resource[] getResources( String name) {
        String target = name.replace('.', '/');
        try {
            return new PathMatchingResourcePatternResolver()
                    .getResources("classpath*:" + target + ".properties");
        }
        catch (Exception ex) {
            return NO_RESOURCES;
        }
    }



    /**
     * 把普通文件名转换成国际化文件名
     *
     * @param filename
     * @return
     */
    private static String getI18FileName(String filename) {
        filename = filename.replace(".properties", "");
        for (int i = 0; i < 2; i++) {
            int index = filename.lastIndexOf("_");
            if (index != -1) {
                filename = filename.substring(0, index);
            }
        }
        return filename.replace("\\", "/");
    }

}
