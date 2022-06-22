package com.ycourlee.tranquil.redisson;

import java.util.Collection;
import java.util.Collections;

/**
 * @author yooonn
 * @date 2022.04.14
 */
public class WaitLockTimeoutException extends RuntimeException {

    private static final long serialVersionUID = -5480544100171718159L;

    private Collection<String> keys = Collections.emptyList();

    public WaitLockTimeoutException() {
        super();
    }

    public WaitLockTimeoutException(String message, Collection<String> keys) {
        super(message);
        this.keys = keys;
    }

    public WaitLockTimeoutException(String message, Collection<String> keys, Throwable cause) {
        super(message, cause);
        this.keys = keys;
    }

    public Collection<String> getKeys() {
        return keys;
    }
}
