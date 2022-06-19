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
public class PlaintextNotAesAlgTest {

    @Plaintext(algorithm = "RSA")
    private String hello;
}
