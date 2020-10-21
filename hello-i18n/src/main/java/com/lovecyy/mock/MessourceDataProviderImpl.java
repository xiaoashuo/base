package com.lovecyy.mock;

import com.lovecyy.config.MessageSourceDataProvider;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;

@Service
public class MessourceDataProviderImpl implements MessageSourceDataProvider {

    @Override
    public Set<String> loadBaseNames() {
        return MockBaseNameData.getAllBaseNames();
    }

    @Override
    public Set<String> loadKeys(String baseName, Locale locale) {
        return MockBaseLocaleData.loadKeysFromCache(baseName,locale);
    }

    @Override
    public String loadVal(String baseName, Locale locale, String key) {
        return MockBaseLocaleData.loadValFromCache(baseName,locale,key);
    }
}
