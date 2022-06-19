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
 * @author yooonn
 */
public class Response extends HashMap<String, Object> {

    public static final  String TIMESTAMP                = "timestamp";
    public static final  String RID                      = "rid";
    public static final  String CODE                     = "code";
    public static final  String MSG                      = "msg";
    public static final  String DATA                     = "data";
    private static final long   serialVersionUID         = 3097207393413111132L;
    private static final int    DEFAULT_INITIAL_CAPACITY = 1 << 3;

    private Response() {
        super(DEFAULT_INITIAL_CAPACITY);
    }

    private Response(Integer code, String msg, Object data) {
        super(DEFAULT_INITIAL_CAPACITY);
        super.put(TIMESTAMP, System.nanoTime());
        super.put(RID, UUID.randomUUID().toString());
        super.put(CODE, code);
        super.put(MSG, msg);
        super.put(DATA, data);
    }

    private Response(CodeMessage cmReturn, Object data) {
        this(cmReturn.getCode(), cmReturn.getMsg(), data);
    }

    private Response(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    private Response(int initialCapacity) {
        super(initialCapacity);
    }

    private Response(Map<? extends String, ?> m) {
        super(m);
    }

    public static Response blanker() {
        return new Response();
    }

    public static Response success() {
        return success(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), null);
    }

    public static Response success(Object data) {
        return success(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), data);
    }

    public static Response success(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }

    public static Response error() {
        return error(ApiCode.ERROR.getCode(), ApiCode.ERROR.getMsg(), null);
    }

    public static Response error(CodeMessage body) {
        return error(body.getCode(), body.getMsg(), null);
    }

    public static Response error(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }

    public Response pin(String key, Object data) {
        super.put(key, data);
        return this;
    }

    public Response code(Object code) {
        super.put(CODE, code);
        return this;
    }

    /*----------BELOW---------- Privatization ----------BELOW----------*/

    public Response msg(Object msg) {
        super.put(MSG, msg);
        return this;
    }

    public Response data(Object data) {
        super.put(DATA, data);
        return this;
    }

    public Response minimize() {
        super.remove(TIMESTAMP);
        super.remove(RID);
        return this;
    }
}
