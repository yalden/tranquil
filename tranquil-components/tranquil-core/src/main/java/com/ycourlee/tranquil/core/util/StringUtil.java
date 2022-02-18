package com.ycourlee.tranquil.core.util;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author jiangyong
 * @date 2020.08.08
 */
public class StringUtil {

    public static final String NULL            = null;
    public static final String EMPTY           = "";
    public static final String WHITESPACE      = " ";
    public static final String SEMINAL         = "-";
    public static final String UNDERLINE       = "_";
    public static final String SLASH           = "/";
    public static final char   WHITESPACE_CHAR = ' ';
    public static final char   SEMINAL_CHAR    = '-';
    public static final char   UNDERLINE_CHAR  = '_';

    /**
     * @param string string
     * @return true if null or ""
     */
    public static boolean isNotNull(String string) {
        return string != null;
    }

    /**
     * @param string string
     * @return true if null or ""
     */
    public static boolean isEmpty(String string) {
        return string == null || EMPTY.equals(string);
    }

    /**
     * @param coll collection obj.
     * @return if not empty
     * @see CollectionUtil#isEmpty(Collection)
     * @since 1.0.7
     */
    @Deprecated
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * @param string string
     * @return true, if characters in string are all ' '
     */
    public static boolean isBlank(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != WHITESPACE_CHAR) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param string string
     * @see #isEmpty(String)
     */
    public static boolean isNotEmpty(String string) {
        return !(string == null || EMPTY.equals(string));
    }

    /**
     * @param coll collection obj.
     * @return if not empty
     * @see CollectionUtil#isNotEmpty(Collection)
     * @since 1.0.7
     */
    @Deprecated
    public static boolean isNotEmpty(Collection<?> coll) {
        return !(coll == null || coll.isEmpty());
    }

    /**
     * @param string string
     * @see #isBlank(String)
     */
    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    /**
     * snake case to camel case.
     *
     * <ul>
     * <li>toCamelCase("revision_world").equals("revisionWorld");</li>
     * <li>toCamelCase("_revision_world").equals("RevisionWorld");</li>
     * <li>toCamelCase("revision_world").equals("revisionWorld");</li>
     * <li>toCamelCase("revision__world").equals("revisionWorld");</li>
     * <li>toCamelCase("revision__woRLd").equals("revisionWoRLd");</li>
     * <li>toCamelCase("revisionwoRLd").equals("revisionwoRLd");</li>
     * </ul>
     *
     * @param src string linked with underline.
     * @return string in camel case.
     */
    public static String toCamelCase(String src) {
        if (src == null || src.isEmpty()) {
            return src;
        }
        if (src.contains(UNDERLINE)) {
            int len = src.length();
            StringBuilder sb = new StringBuilder();
            boolean eatAnUnderline = false;
            for (int i = 0; i < len; i++) {
                char c = src.charAt(i);
                if (c == UNDERLINE_CHAR) {
                    eatAnUnderline = true;
                } else if (eatAnUnderline && Character.isLetter(c)) {
                    sb.append(Character.toUpperCase(c));
                    eatAnUnderline = false;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return src;
        }
    }
}