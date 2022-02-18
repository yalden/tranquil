package com.ycourlee.tranquil.web;

/**
 * Server Response Standard.
 * <p>
 * 响应码类需要实现此接口
 *
 * @author yooonnjiang
 * @date 2020.09.21
 */
public interface CodeMessage {

    /**
     * return the code.
     *
     * @return code
     */
    int getCode();

    /**
     * return the message。
     *
     * @return message
     */
    String getMsg();
}
