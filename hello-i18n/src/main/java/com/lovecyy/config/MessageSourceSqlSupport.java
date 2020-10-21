package com.lovecyy.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息源扩展
 * HierarchicalMessageSource接口添加了两个方法，建立父子层级的MessageSource结构，类似于前面我们所介绍的HierarchicalBeanFactory。
 * <p>
 * 该接口的setParentMessageSource (MessageSource parent)方法用于设置父MessageSource，而getParentMessageSource()方法用于返回父MessageSource。
 * HierarchicalMessageSource接口最重要的两个实现类是ResourceBundleMessageSource和ReloadableResourceBundleMessageSource。它们基于Java的ResourceBundle基础类实现，允许仅通过资源名加载国际化资源。
 * ReloadableResourceBundleMessageSource提供了定时刷新功能，允许在不重启系统的情况下，更新资源的信息。
 * 在上面的配置中，我们通过cacheSeconds属性让ReloadableResourceBundleMessageSource每5秒钟刷新一次资源文件
 * （在真实的应用中，刷新周期不能太短，否则频繁的刷新将带来性能上的负面影响，一般不建议小于30分钟）。cacheSeconds默认值为-1表示永不刷新，此时，该实现类的功能就蜕化为ResourceBundleMessageSource的功能。
 *
 * @author shuoyu
 */
@Slf4j
@RequiredArgsConstructor
public class MessageSourceSqlSupport extends ResourceBundleMessageSource implements MessageSourceHotLoading {


    //消息源属性
    private final MessageSourcePropertiesExtension messageSourceProperties;
    //数据源提提供
    private final MessageSourceDataProvider messageSourceDataProvider;

//    private final LocaleCache localeCache;

    /**
     * Cache to hold loaded ResourceBundles.
     * This Map is keyed with the bundle basename, which holds a Map that is
     * keyed with the Locale and in turn holds the ResourceBundle instances.
     * This allows for very efficient hash lookups, significantly faster
     * than the ResourceBundle class's own cache.
     * basename  key  locale  val
     */
    private final Map<String, Map<String, Map<Locale, String>>> cachedNoArgsVals =
            new ConcurrentHashMap<>();
    /**
     * Cache to hold already generated MessageFormats.
     * This Map is keyed with the ResourceBundle, which holds a Map that is
     * keyed with the message code, which in turn holds a Map that is keyed
     * with the Locale and holds the MessageFormat values. This allows for
     * very efficient hash lookups without concatenated keys.
     *
     * @see #getMessageFormat
     */
    private final Map<String, Map<String, Map<Locale, MessageFormat>>> cachedBaseMessageFormats =
            new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Set<String> allBaseNames = messageSourceDataProvider.loadBaseNames();
        if (!CollectionUtils.isEmpty(allBaseNames)) {
            this.setBasenames(allBaseNames.toArray(new String[0]));
            fillMessageSource(this, messageSourceProperties);
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
        fillMessageSource(parent, messageSourceProperties);
        this.setParentMessageSource(parent);
    }

    @Override
    public void reloadBaseNames() {
        logger.info("开始重新加载所有的baseNames");
        Set<String> allBaseNames = messageSourceDataProvider.loadBaseNames();
        if (!CollectionUtils.isEmpty(allBaseNames)) {
            this.setBasenames(allBaseNames.toArray(new String[0]));
        }
    }

    /**
     * 填充消息源
     *
     * @param resourceBundleMessageSource
     * @param messageSourceProperties
     */
    private void fillMessageSource(ResourceBundleMessageSource resourceBundleMessageSource, MessageSourceProperties messageSourceProperties) {
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
     * Resolves the given message code as key in the registered resource bundles,
     * returning the value found in the bundle as-is (without MessageFormat parsing).
     */
    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        Set<String> basenames = getBasenameSet();
        for (String basename : basenames) {
            String result = null;
            if (messageSourceProperties.isLocalCache()) {
                //开启本地缓存
                result = getNoArgsBundleValue(basename, locale, code);
            } else {
                result = getValFromCache(basename, locale, code);
            }
            if (!StringUtils.isEmpty(result)) {
                return result;
            }
        }
        return null;
    }

    private String getValFromCache(String basename, Locale locale, String key) {
        // Cache forever: prefer locale cache over repeated getBundle calls.
        try {
            //缓存用户自定义
            String val = messageSourceDataProvider.loadVal(basename, locale, key);
            return val;
        } catch (MissingResourceException ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("ResourceBundle [" + basename + "] not found for MessageSource: " + ex.getMessage());
            }
            // Assume bundle not found
            // -> do NOT throw the exception to allow for checking parent message source.
            return null;
        }
    }


    /**
     * Resolves the given message code as key in the registered resource bundles,
     * using a cached MessageFormat instance per message code.
     */
    @Override
    @Nullable
    protected MessageFormat resolveCode(String code, Locale locale) {
        Set<String> basenames = getBasenameSet();
        for (String basename : basenames) {
            MessageFormat messageFormat;
            if (messageSourceProperties.isLocalCache()) {
                messageFormat = getArgsMessageFormat(basename, code, locale);
            } else {
                messageFormat = getValFromParamCache(basename, locale, code);
            }
            if (messageFormat != null) {
                return messageFormat;
            }
        }
        return null;
    }

    private MessageFormat getValFromParamCache(String basename, Locale locale, String code) {
        String result = messageSourceDataProvider.loadVal(basename, locale, code);
        if (!StringUtils.isEmpty(result)) {
            MessageFormat messageFormat = createMessageFormat(result, locale);
            return messageFormat;
        }
        return null;
    }

    /**
     * Return a ResourceBundle for the given basename and code,
     * fetching already generated MessageFormats from the cache.
     *
     * @param basename the basename of the ResourceBundle
     * @param locale   the Locale to find the ResourceBundle for
     * @return the resulting ResourceBundle, or {@code null} if none
     * found for the given basename and Locale
     */

    @Nullable
    protected String getNoArgsBundleValue(String basename, Locale locale, String code) {
        //key ->en_US val
        Map<String, Map<Locale, String>> codeMap = this.cachedNoArgsVals.get(basename);
        Map<Locale, String> localeMap = null;
        if (codeMap != null) {
            localeMap = codeMap.get(code);
            if (localeMap != null) {
                String result = localeMap.get(locale);
                if (result != null) {
                    return result;
                }
            }
        }
        String msg = messageSourceDataProvider.loadVal(basename, locale, code);
        if (!StringUtils.isEmpty(msg)) {
            if (codeMap == null) {
                codeMap = new ConcurrentHashMap<>();
                Map<String, Map<Locale, String>> existing =
                        this.cachedNoArgsVals.putIfAbsent(basename, codeMap);
                if (existing != null) {
                    codeMap = existing;
                }
            }
            if (localeMap == null) {
                localeMap = new ConcurrentHashMap<>();
                Map<Locale, String> existing = codeMap.putIfAbsent(code, localeMap);
                if (existing != null) {
                    localeMap = existing;
                }
            }
            localeMap.put(locale, msg);
            return msg;
        }
        return null;

    }


    /**
     * Return a MessageFormat for the given bundle and code,
     * fetching already generated MessageFormats from the cache.
     *
     * @param baseName the baseName
     * @param code     the message code to retrieve
     * @param locale   the Locale to use to build the MessageFormat
     * @return the resulting MessageFormat, or {@code null} if no message
     * defined for the given code
     * @throws MissingResourceException if thrown by the ResourceBundle
     */
    @Nullable
    protected MessageFormat getArgsMessageFormat(String baseName, String code, Locale locale)
            throws MissingResourceException {
        //key en_US 格式
        Map<String, Map<Locale, MessageFormat>> codeMap = this.cachedBaseMessageFormats.get(baseName);
        Map<Locale, MessageFormat> localeMap = null;
        if (codeMap != null) {
            localeMap = codeMap.get(code);
            if (localeMap != null) {
                MessageFormat result = localeMap.get(locale);
                if (result != null) {
                    return result;
                }
            }
        }
        String msg = messageSourceDataProvider.loadVal(baseName, locale, code);
        if (msg != null) {
            if (codeMap == null) {
                codeMap = new ConcurrentHashMap<>();
                Map<String, Map<Locale, MessageFormat>> existing =
                        this.cachedBaseMessageFormats.putIfAbsent(baseName, codeMap);
                if (existing != null) {
                    codeMap = existing;
                }
            }
            if (localeMap == null) {
                localeMap = new ConcurrentHashMap<>();
                Map<Locale, MessageFormat> existing = codeMap.putIfAbsent(code, localeMap);
                if (existing != null) {
                    localeMap = existing;
                }
            }
            MessageFormat result = createMessageFormat(msg, locale);
            localeMap.put(locale, result);
            return result;
        }

        return null;
    }


    @Override
    public void clearLocalCache() {
        this.cachedNoArgsVals.clear();
        this.cachedBaseMessageFormats.clear();
    }


}
