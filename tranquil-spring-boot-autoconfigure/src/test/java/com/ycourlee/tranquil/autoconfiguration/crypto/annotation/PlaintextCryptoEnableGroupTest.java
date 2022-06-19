package com.ycourlee.tranquil.autoconfiguration.crypto.annotation;

import com.ycourlee.tranquil.crypto.annotation.Plaintext;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yooonn
 * @date 2021.12.16
 */
@Setter
@Getter
public class PlaintextCryptoEnableGroupTest {

    @Plaintext(group = "1")
    private String hello;

    @Plaintext(group = "2")
    private String world;
}
