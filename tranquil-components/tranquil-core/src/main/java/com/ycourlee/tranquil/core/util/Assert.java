package com.ycourlee.tranquil.core.util;


import com.ycourlee.tranquil.core.exception.AssertException;

import java.util.Collection;

/**
 * @author yongjiang
 * @date 2021.12.10
 */
public abstract class Assert {

    private static final String ASSERT_ERROR_MESSAGE_DEFAULT = "expression assert error! ";

    /**
     * @param obj    obj
     * @param errMsg error message, transferred when expr is false
     * @throws AssertException thrown when expr is false
     */
    public static void notNull(Object obj, String errMsg) {
        if (obj == null) {
            throw new AssertException(errMsg);
        }
    }

    /**
     * @param obj obj
     * @see #notNull(Object, String)
     */
    public static void notNull(Object obj) {
        if (obj == null) {
            throw new AssertException(ASSERT_ERROR_MESSAGE_DEFAULT);
        }
    }

    /**
     * @param expr   expr
     * @param errMsg error message, transferred when expr is false
     * @throws AssertException thrown when expr is false
     */
    public static void that(boolean expr, String errMsg) {
        if (!expr) {
            throw new AssertException(errMsg);
        }
    }

    /**
     * @param expr expr
     * @see #that(boolean, String)
     */
    public static void that(boolean expr) {
        if (!expr) {
            throw new AssertException(ASSERT_ERROR_MESSAGE_DEFAULT);
        }
    }

    /**
     * @param expr   expr
     * @param errMsg error message, transferred when expr is false
     * @throws AssertException thrown when expr is false
     */
    public static void impossible(boolean expr, String errMsg) {
        if (expr) {
            throw new AssertException(errMsg);
        }
    }

    /**
     * @param expr expr
     * @see #that(boolean, String)
     */
    public static void impossible(boolean expr) {
        if (expr) {
            throw new AssertException(ASSERT_ERROR_MESSAGE_DEFAULT);
        }
    }

    /*----------BELOW---------- is not empty about ----------BELOW----------*/

    /**
     * @param string string
     * @param errMsg error message, transferred when string is empty
     * @throws AssertException thrown when string is null or ""
     */
    public static void notEmpty(String string, String errMsg) {
        if (string == null || StringUtil.EMPTY.equals(string)) {
            throw new AssertException(errMsg);
        }
    }

    /**
     * @param string string
     * @see #notEmpty(String, String)
     */
    public static void notEmpty(String string) {
        if (string == null || StringUtil.EMPTY.equals(string)) {
            throw new AssertException(ASSERT_ERROR_MESSAGE_DEFAULT);
        }
    }

    /**
     * 断言coll是not empty的
     *
     * @param coll   collection
     * @param errMsg error message
     * @throws AssertException thrown when coll is null or no element in it.
     */
    public static void notEmpty(Collection<?> coll, String errMsg) {
        if (coll == null || coll.size() < 1) {
            throw new AssertException(errMsg);
        }
    }

    /**
     * @param coll collection
     * @see #notEmpty(Collection, String)
     */
    public static void notEmpty(Collection<?> coll) {
        if (coll == null || coll.size() < 1) {
            throw new AssertException(ASSERT_ERROR_MESSAGE_DEFAULT);
        }
    }

    /**
     * @param string string
     * @param errMsg error message, transferred when string is blank.
     * @throws AssertException thrown when string is blank.
     */
    public static void notBlank(String string, String errMsg) {
        if (StringUtil.isBlank(string)) {
            throw new AssertException(errMsg);
        }
    }

    /**
     * @param string string
     * @see #notBlank(String, String)
     */
    public static void notBlank(String string) {
        if (StringUtil.isBlank(string)) {
            throw new AssertException(ASSERT_ERROR_MESSAGE_DEFAULT);
        }
    }
}