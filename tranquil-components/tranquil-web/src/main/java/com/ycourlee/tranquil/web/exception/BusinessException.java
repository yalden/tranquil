package com.ycourlee.tranquil.web.exception;

import com.ycourlee.tranquil.web.ApiCode;
import com.ycourlee.tranquil.web.CodeMessage;

/**
 * @author yooonnjiang
 * @date 2020.09.29
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -2625243679651825090L;

    private CodeMessage cmReturn;

    public BusinessException() {
    }

    public BusinessException(CodeMessage cmReturn) {
        this.cmReturn = cmReturn;
    }

    public BusinessException(final int code, final String message) {
        super(message);
        this.cmReturn = new CodeMessage() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMsg() {
                return message;
            }
        };
    }

    public CodeMessage getCmReturn() {
        return cmReturn;
    }

    public static class DefaultBusinessException extends BusinessException {

        private static final long serialVersionUID = -1180058052339138826L;

        public DefaultBusinessException() {
            super(ApiCode.DEFAULT_BUSINESS_ERROR);
        }
    }
}
