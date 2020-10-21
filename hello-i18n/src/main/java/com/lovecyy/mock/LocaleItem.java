package com.lovecyy.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域项
 * @author Yakir
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LocaleItem {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 基础目录名
     */
    private String baseName;
    /**
     * 区域码
     */
    private String locale;
    /**
     * 参数key
     */
    private String key;
    /**
     * 参数值
     */
    private String value;

}
