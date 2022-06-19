package com.ycourlee.tranquil.autoconfiguration.redisson;

/**
 * @author yooonn
 * @date 2022.04.14
 */
public class WaitLockTimeoutException extends RuntimeException {

    private static final long serialVersionUID = -5480544100171718159L;

    public WaitLockTimeoutException() {
        super();
    }

    public WaitLockTimeoutException(String message) {
        super(message);
    }

    public WaitLockTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
