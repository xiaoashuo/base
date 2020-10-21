package com.lovecyy.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 消息源扩展配置
 *
 * @author shuoyu
 */
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class MessageSourcePlusConfig {

    @Bean
    @ConfigurationProperties(prefix = "i18n.messages")
    public MessageSourcePropertiesExtension messageSourcePropertiesExtension() {
        return new MessageSourcePropertiesExtension();
    }


    /**
     * 普通消息源
     * @param messageSourcePropertiesExtension
     * @return
     */
    @Bean("messageSource")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "i18n.messages",matchIfMissing = true, name = "supportSql",havingValue = "false")
    public MessageSource messageSource(MessageSourcePropertiesExtension messageSourcePropertiesExtension){
        return new MessageSourceExtension(messageSourcePropertiesExtension);
    }

    /**
     * sql消息源 没用默认走message配置
     * @param messageSourcePropertiesExtension
     * @param messageSourceDataProvider
     * @return
     */
    @Bean("messageSource")
    @ConditionalOnClass(MessageSourceDataProvider.class)
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "i18n.messages",name = "supportSql",havingValue = "true")
    public MessageSource messageSourceSql(MessageSourcePropertiesExtension messageSourcePropertiesExtension, MessageSourceDataProvider messageSourceDataProvider) {
        return new MessageSourceSqlSupport(messageSourcePropertiesExtension,messageSourceDataProvider);
    }
}
