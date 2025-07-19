package com.ai.sys.utils;

import org.springframework.util.StringUtils;

/**
 * @author pengYuJun
 */
public class CommonUtils {

    /**
     * 判断是否存在字符串为空
     * @return boolean
     */
    public static boolean isAnyBlank(String... strs) {
        for (String str : strs) {
            if (!StringUtils.hasText(str)) {
                return true;
            }
        }
        return false;
    }

}
