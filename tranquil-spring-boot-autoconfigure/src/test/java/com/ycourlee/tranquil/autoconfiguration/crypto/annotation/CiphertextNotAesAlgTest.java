package com.ycourlee.tranquil.autoconfiguration.crypto.annotation;

import com.ycourlee.tranquil.crypto.Algorithms;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yongjiang
 * @date 2021.12.16
 */
@Setter
@Getter
public class CiphertextNotAesAlgTest {

    @Ciphertext(algorithm = Algorithms.RSA)
    private String hello;
}
