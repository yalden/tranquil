package com.ycourlee.tranquil.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author yongjiang
 * @date 2021.12.11
 */
public class StarMaskerTest {

    @Test
    void mainTest() {
        String nil = StarMasker.mask(null, 3);
        assertEquals(nil, "null");
        String empty = StarMasker.mask("", 3);
        assertEquals(empty, "");
        String hello = StarMasker.mask("hello", 3);
        assertEquals(hello, "hel**");
        String helloWorld = StarMasker.mask("helloWorld", 5);
        assertEquals(helloWorld, "hello***");
    }
}
