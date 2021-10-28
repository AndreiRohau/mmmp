package com.jwd.controller.util;

import static java.util.Objects.isNull;

public class Util {
    public static boolean isNullOrEmpty(final String s) {
        return isNull(s) || "".equals(s);
    }

    public static boolean isNullOrEmpty(final char[] s) {
        return isNull(s) || s.length == 0;
    }
}
