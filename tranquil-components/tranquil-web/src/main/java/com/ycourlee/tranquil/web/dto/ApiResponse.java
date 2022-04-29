package com.ycourlee.tranquil.web.dto;

import com.alibaba.fastjson.JSON;
import com.ycourlee.tranquil.web.ApiCode;
import com.ycourlee.tranquil.web.CodeMessage;

import java.io.Serializable;

/**
 * api response definition.
 *
 * @author jiangyong
 * @date 2020.08.28
 */
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final ApiResponse<Object> SUCCESS_NULL_DATA = new ApiResponse<>(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), null);

    private static final ApiResponse<Object> ERROR_NULL_DATA = new ApiResponse<>(ApiCode.ERROR.getCode(), ApiCode.ERROR.getMsg(), null);

    private int code;

    private String message;

    private T data;

    private ApiResponse() {
    }

    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse<Object> success() {
        return SUCCESS_NULL_DATA;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), data);
    }

    public static ApiResponse<Object> error() {
        return ERROR_NULL_DATA;
    }

    public static <T> ApiResponse<T> error(CodeMessage rt) {
        return new ApiResponse<>(rt.getCode(), rt.getMsg(), null);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public int getCode() {
        return code;
    }

    public ApiResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
