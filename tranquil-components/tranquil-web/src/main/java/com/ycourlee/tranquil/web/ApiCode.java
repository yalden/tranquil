package com.ycourlee.tranquil.web;

/**
 * API通用返回枚举
 * api common return(response) enums.
 *
 * @author yooonn
 * @date 2020.09.29
 */
public enum ApiCode implements CodeMessage {
    /**
     * success
     */
    SUCCESS(0, "ok"),
    DEFAULT_BUSINESS_ERROR(1, "未知业务异常"),
    ERROR(88, "error"),

    ;

    private final int code;

    private final String msg;

    ApiCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
