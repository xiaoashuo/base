package com.lovecyy.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 消息源扩展配置
 * @author shuoyu
 */
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class MessageSourcePlusConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourcePropertiesExtension messageSourceProperties() {
        return new MessageSourcePropertiesExtension();
    }

    @Bean
    public MessageSource messageSource(){
        return new MessageSourceExtension(messageSourceProperties());
    }
}
