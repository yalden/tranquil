package com.ycourlee.tranquil.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yooonn
 * @date 2022.02.19
 */
public class StringUtilTest {

    @Test
    void toCamelCaseTest() {
        // noinspection ConstantConditions
        String a = StringUtil.toCamelCase(null);
        // noinspection ConstantConditions
        assertNull(a);

        String b = StringUtil.toCamelCase("");
        assertTrue(b.isEmpty());

        assertEquals("revisionWorld", StringUtil.toCamelCase("revision_world"));
        assertEquals("RevisionWorld", StringUtil.toCamelCase("_revision_world"));
        assertEquals("revisionWorld", StringUtil.toCamelCase("revision_world"));
        assertEquals("revisionWorld", StringUtil.toCamelCase("revision__world"));
        assertEquals("revisionWoRLd", StringUtil.toCamelCase("revision__woRLd"));
        assertEquals("revisionwoRLd", StringUtil.toCamelCase("revisionwoRLd"));
    }
}
