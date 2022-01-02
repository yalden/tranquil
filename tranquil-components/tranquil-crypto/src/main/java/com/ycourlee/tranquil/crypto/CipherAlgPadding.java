package com.ycourlee.tranquil.crypto;

/**
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
 * Cipher Algorithm Padding</a>
 *
 * @author yongjiang
 * @date 2021.12.22
 */
public enum CipherAlgPadding {

    NoPadding,

    PKCS1Padding,

    PKCS5Padding,

    /**
     * Provide by bouncycastle.
     */
    PKCS7Padding,
}
