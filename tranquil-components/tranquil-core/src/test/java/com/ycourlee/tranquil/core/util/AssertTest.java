package com.ycourlee.tranquil.core.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author yongjiang
 * @date 2022.02.19
 */
public class AssertTest {

    private static final Logger log = LoggerFactory.getLogger(AssertTest.class);

    @Test void mainTest() {
        Integer a = 3;
        Integer b = get2();

        assertThrows(Exception.class, () -> Assert.that(a.equals(b), () -> {
            log.error("error");
            throw new IllegalStateException();
        }));
    }

    private Integer get2() {
        return 2;
    }
}
