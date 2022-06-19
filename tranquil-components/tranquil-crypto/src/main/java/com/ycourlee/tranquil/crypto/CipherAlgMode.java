package com.ycourlee.tranquil.crypto;

import com.google.common.annotations.Beta;

/**
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
 * Cipher Algorithm Modes</a>
 *
 * @author yooonn
 * @date 2021.12.22
 */
public enum CipherAlgMode {

    /**
     * Cipher Block Chaining，密码块链接
     */
    CBC,

    /**
     * Electronic Codebook，电子密码本
     */
    ECB,
}
