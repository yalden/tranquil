package com.ycourlee.tranquil.web.dto;

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
        return success(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), data);
    }

    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public static ApiResponse<Object> error() {
        return ERROR_NULL_DATA;
    }

    public static <T> ApiResponse<T> error(CodeMessage rt) {
        return error(rt.getCode(), rt.getMsg(), null);
    }

    public static <T> ApiResponse<T> error(int code, String message, T date) {
        return new ApiResponse<>(code, message, date);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
