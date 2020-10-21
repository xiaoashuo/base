package com.lovecyy.utils;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtils {

    private static MessageSource messageSource;

    @Autowired
    public  void setMessageSource(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }
    public static String getMessage(String code){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, code, locale);
    }
    public static String getMessage(String code,Object[] params){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, params, code, locale);
    }
    public static String getMessage(String code,Object[] params,Locale locale){
        return messageSource.getMessage(code, params, code, locale);
    }
    public String getMessage(String code, Object[] args, String defaultParam, Locale locale){
        return messageSource.getMessage(code, args, defaultParam, locale);
    }
}
