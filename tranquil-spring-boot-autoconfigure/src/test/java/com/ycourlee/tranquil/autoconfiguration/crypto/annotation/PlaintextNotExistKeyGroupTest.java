package com.ycourlee.tranquil.autoconfiguration.crypto.annotation;

import com.ycourlee.tranquil.crypto.annotation.Plaintext;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yongjiang
 * @date 2021.12.16
 */
@Setter
@Getter
public class PlaintextNotExistKeyGroupTest {

    @Plaintext(keyGroup = "group1000")
    private String hello;
}
