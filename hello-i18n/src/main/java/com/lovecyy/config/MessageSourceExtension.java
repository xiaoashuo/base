package com.lovecyy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

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
@Component("messageSource")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class MessageSourceExtension extends ResourceBundleMessageSource {
    private static final Resource[] NO_RESOURCES = {};



    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourcePropertiesExtension messageSourceProperties() {
        return new MessageSourcePropertiesExtension();
    }

    @Autowired
    private MessageSourcePropertiesExtension messageSourceProperties;

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
