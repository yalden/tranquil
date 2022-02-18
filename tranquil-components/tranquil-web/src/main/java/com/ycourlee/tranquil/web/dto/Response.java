package com.ycourlee.tranquil.web.dto;

import com.ycourlee.tranquil.web.ApiCode;
import com.ycourlee.tranquil.web.CodeMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Return data Map.
 *
 * <a href="https://gitee.com/y_project/RuoYi/blob/master/ruoyi-common/src/main/java/com/ruoyi/common/core/domain/AjaxResult.java">
 * Idea from source code, AjaxResult.java</a>
 *
 * @author yongjiang
 */
public class Response<T> extends HashMap<String, Object> {

    private static final long serialVersionUID         = 3097207393413111132L;
    private static final int  DEFAULT_INITIAL_CAPACITY = 1 << 3;

    public static final String TIMESTAMP = "timestamp";
    public static final String RID       = "rid";
    public static final String CODE      = "code";
    public static final String MSG       = "msg";
    public static final String DATA      = "data";

    private Response() {
        super(DEFAULT_INITIAL_CAPACITY);
    }

    private Response(Integer code, String msg, T data) {
        super(DEFAULT_INITIAL_CAPACITY);
        super.put(TIMESTAMP, System.nanoTime());
        super.put(RID, UUID.randomUUID().toString());
        super.put(CODE, code);
        super.put(MSG, msg);
        super.put(DATA, data);
    }

    private Response(CodeMessage cmReturn, T data) {
        this(cmReturn.getCode(), cmReturn.getMsg(), data);
    }

    public Response<T> pin(String key, Object data) {
        super.put(key, data);
        return this;
    }

    public Response<T> code(Object code) {
        super.put(CODE, code);
        return this;
    }

    public Response<T> msg(Object msg) {
        super.put(MSG, msg);
        return this;
    }

    public Response<T> data(Object data) {
        super.put(DATA, data);
        return this;
    }

    public Response<T> minimize() {
        super.remove(TIMESTAMP);
        super.remove(RID);
        return this;
    }

    public static <T> Response<T> blanker() {
        return new Response<>();
    }

    public static <T> Response<T> success() {
        return success(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), null);
    }

    public static <T> Response<T> success(T data) {
        return success(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), data);
    }

    public static <T> Response<T> success(Integer code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> error() {
        return error(ApiCode.ERROR.getCode(), ApiCode.ERROR.getMsg(), null);
    }

    public static <T> Response<T> error(CodeMessage body) {
        return error(body.getCode(), body.getMsg(), null);
    }

    public static <T> Response<T> error(Integer code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    /*----------BELOW---------- Privatization ----------BELOW----------*/

    private Response(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    private Response(int initialCapacity) {
        super(initialCapacity);
    }

    private Response(Map<? extends String, ?> m) {
        super(m);
    }
}
