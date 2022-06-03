package com.ycourlee.tranquil.crypto.exception;

/**
 * @author yoooonn
 * @date 2021.12.16
 */
public class CryptoException extends RuntimeException {

    private static final long serialVersionUID = 2978094524735152681L;

    public CryptoException() {
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(Throwable cause) {
        super(cause);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
