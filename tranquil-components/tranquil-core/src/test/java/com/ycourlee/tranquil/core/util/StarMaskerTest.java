package com.ycourlee.tranquil.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author yooonn
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
        assertEquals("this i***", StarMasker.mask("this is a text.", 6, true));
        assertEquals("this i*********", StarMasker.mask("this is a text.", 6, false));
        assertEquals("null", StarMasker.mask(null, 6, false));
        assertEquals("***is a text.", StarMasker.mask(5, "this is a text."));
        assertEquals("***is a text.", StarMasker.mask(5, "this is a text.", true));
        assertEquals("this is a text.", StarMasker.mask("this is a text.", 100));
        assertEquals("this is a text.", StarMasker.mask("this is a text.", 100, 1, false));
        assertEquals("this is a text.", StarMasker.mask("this is a text.", 6, 1, false));
    }
}
