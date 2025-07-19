package com.ai.sys.common;

import lombok.Getter;

/**
 * 服务响应信息枚举类
 *
 * @author peng_YuJun (邮箱: pengyujun53@163.com)
 * @since 2022/9/18
 */
@Getter
public enum ResponseCode {

    /**
     * 操作成功
     */
    SUCCESS("操作成功", 23200),
    /**
     * 参数错误
     */
    PARAMS_ERROR("参数错误", 23400),
    /**
     * 未登录
     */
    UNAUTHORIZED("未登录", 23401),
    /**
     * 操作无权限
     */
    FORBIDDEN("操作无权限", 23403),
    /**
     * 系统内部异常
     */
    ERROR("系统内部异常", 23500);

    private final String message;
    private final long code;

    ResponseCode(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
